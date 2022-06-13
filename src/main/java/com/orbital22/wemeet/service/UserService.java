package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.solver.RosterPlanUserPlanningEntity;
import com.orbital22.wemeet.solver.RosterPlanningSolution;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;

  @NotNull
  public Optional<User> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName());
  }

  @NotNull
  public void setSessionUser(User user) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities()));
  }

  @NotNull
  private RosterPlanUserPlanningEntity createRosterPlanUserFrom(RosterPlan rosterPlan, User user) {
    return RosterPlanUserPlanningEntity.builder()
        .id(user.getId())
        .rankMap(
            timeSlotUserInfoRepository.findByRosterPlanAndUser(rosterPlan, user).stream()
                .collect(
                    Collectors.toMap(TimeSlotUserInfo::getTimeSlot, TimeSlotUserInfo::getRank)))
        .build();
  }

  @NotNull
  public RosterPlanningSolution createRosterPlanningSolutionFrom(RosterPlan rosterPlan) {
    return RosterPlanningSolution.builder()
        .id(rosterPlan.getId())
        .owner(rosterPlan.getOwner())
        .timeSlots(rosterPlan.getTimeSlots())
        .rosterPlanUsers(
            rosterPlan.getRosterPlanUserInfos().stream()
                .map(
                    rosterPlanUserInfo ->
                        createRosterPlanUserFrom(rosterPlan, rosterPlanUserInfo.getUser()))
                .collect(Collectors.toSet()))
        .build();
  }

  @NotNull
  private Optional<TimeSlotUserInfo> findTimeSlotUserInfoFrom(
      RosterPlanUserPlanningEntity rosterPlanUser) {
    return userRepository
        .findById(rosterPlanUser.getId())
        .flatMap(
            user ->
                timeSlotUserInfoRepository.findByTimeSlotAndUser(
                    rosterPlanUser.getTimeSlot(), user));
  }

  @NotNull
  private Optional<RosterPlanUserInfo> findRosterPlanUserInfoFrom(
      RosterPlan rosterPlan, RosterPlanUserPlanningEntity rosterPlanUser) {
    return userRepository
        .findById(rosterPlanUser.getId())
        .flatMap(user -> rosterPlanUserInfoRepository.findByRosterPlanAndUser(rosterPlan, user));
  }

  public void updateTimeSlotUserInfosFromSolution(RosterPlanningSolution solution) {
    timeSlotUserInfoRepository.saveAll(
        () ->
            solution.getRosterPlanUsers().stream()
                .filter(rosterPlanUser -> rosterPlanUser.getTimeSlot() != null)
                .map(rosterPlanUser -> findTimeSlotUserInfoFrom(rosterPlanUser).orElseThrow())
                .peek(timeSlotUserInfo -> timeSlotUserInfo.setPicked(true))
                .iterator());
  }

  public void updateRosterPlanUserInfosFromSolution(
      RosterPlanningSolution solution, RosterPlan rosterPlan) {
    rosterPlanUserInfoRepository.saveAll(
        () ->
            solution.getRosterPlanUsers().stream()
                .filter(rosterPlanUser -> rosterPlanUser.getTimeSlot() != null)
                .map(
                    rosterPlanUser ->
                        findRosterPlanUserInfoFrom(rosterPlan, rosterPlanUser).orElseThrow())
                .peek(rosterPlanUserInfo -> rosterPlanUserInfo.setLocked(true))
                .iterator());
  }
}
