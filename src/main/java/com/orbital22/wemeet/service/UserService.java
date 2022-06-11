package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.UserDto;
import com.orbital22.wemeet.mapper.TimeSlotMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.solver.RosterPlanUserPlanningEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;
  private final LocalValidatorFactoryBean validator;

  public void validate(UserDto e) {
    Errors errors = new BeanPropertyBindingResult(e, e.getClass().getName());
    validator.validate(e, errors);

    // To refactor these two conditions
    // such that validation can be settled with bean alone
    if (e.isRegistered() ^ (e.getPassword() != null)) {
      errors.reject("REGISTERED_IFF_PASSWORD_NULL");
    }
    if (userRepository.findByEmail(e.getEmail()).map(User::isRegistered).orElse(false)) {
      errors.reject("USER_ALREADY_REGISTERED");
    }

    if (errors.hasErrors()) {
      throw new RepositoryConstraintViolationException(errors);
    }
  }

  @NonNull
  public Optional<User> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName());
  }

  @NonNull
  public RosterPlanUserPlanningEntity from(RosterPlan rosterPlan, User user) {
    return RosterPlanUserPlanningEntity.builder()
        .id(user.getId())
        .rankMap(
            timeSlotUserInfoRepository.findByRosterPlanAndUser(rosterPlan, user).stream()
                .collect(
                    Collectors.toMap(
                        info -> TimeSlotMapper.INSTANCE.timeSlotToTimeSlotDto(info.getTimeSlot()),
                        TimeSlotUserInfo::getRank)))
        .build();
  }
}
