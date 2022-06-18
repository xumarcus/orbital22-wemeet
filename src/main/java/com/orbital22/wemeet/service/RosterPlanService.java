package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private final RosterPlanRepository rosterPlanRepository;
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private final TimeSlotRepository timeSlotRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;

  public void deepCopy(RosterPlan source, RosterPlan target) {
    assert (source != null);

    target.setTimeSlots(
        new HashSet<>(
            timeSlotRepository.saveAll(
                () ->
                    source.getTimeSlots().stream()
                        .map(
                            timeSlot -> {
                              // No aspect (yet)
                              TimeSlot cloned =
                                  timeSlotRepository.save(
                                      TimeSlot.builder()
                                          .rosterPlan(target)
                                          .startDateTime(timeSlot.getStartDateTime())
                                          .endDateTime(timeSlot.getEndDateTime())
                                          .capacity(timeSlot.getCapacity())
                                          .build());
                              Set<TimeSlotUserInfo> timeSlotUserInfos =
                                  new HashSet<>(
                                      timeSlotUserInfoRepository.saveAll(
                                          () ->
                                              timeSlot.getTimeSlotUserInfos().stream()
                                                  .map(
                                                      timeSlotUserInfo ->
                                                          TimeSlotUserInfo.builder()
                                                              .timeSlot(cloned)
                                                              .user(timeSlotUserInfo.getUser())
                                                              .rank(timeSlotUserInfo.getRank())
                                                              .build())
                                                  .iterator()));
                              cloned.setTimeSlotUserInfos(timeSlotUserInfos);
                              return cloned;
                            })
                        .iterator())));

    target.setRosterPlanUserInfos(
        new HashSet<>(
            rosterPlanUserInfoRepository.saveAll(
                () ->
                    source.getRosterPlanUserInfos().stream()
                        .map(
                            rosterPlanUserInfo ->
                                RosterPlanUserInfo.builder()
                                    .rosterPlan(target)
                                    .user(rosterPlanUserInfo.getUser())
                                    .locked(rosterPlanUserInfo.isLocked())
                                    .build())
                        .iterator())));
  }

  public RosterPlan justSave(RosterPlan rosterPlan) {
    return rosterPlanRepository.saveAll(Collections.singleton(rosterPlan)).get(0);
  }
}
