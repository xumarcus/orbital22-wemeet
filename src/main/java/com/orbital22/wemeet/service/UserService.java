package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.solver.RosterPlanUserPlanningEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;

  @NotNull
  public RosterPlanUserPlanningEntity from(RosterPlan rosterPlan, User user) {
    return RosterPlanUserPlanningEntity.builder()
        .id(user.getId())
        .rankMap(
            timeSlotUserInfoRepository.findByRosterPlanAndUser(rosterPlan, user).stream()
                .collect(
                    Collectors.toMap(TimeSlotUserInfo::getTimeSlot, TimeSlotUserInfo::getRank)))
        .build();
  }

  @NotNull
  public Optional<User> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName());
  }
}
