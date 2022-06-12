package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.solver.RosterPlanUserPlanningEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;

  @NonNull
  public RosterPlanUserPlanningEntity from(RosterPlan rosterPlan, User user) {
    return RosterPlanUserPlanningEntity.builder()
        .id(user.getId())
        .rankMap(
            timeSlotUserInfoRepository.findByRosterPlanAndUser(rosterPlan, user).stream()
                .collect(
                    Collectors.toMap(TimeSlotUserInfo::getTimeSlot, TimeSlotUserInfo::getRank)))
        .build();
  }
}
