package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanPublishRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.util.ExceptionHelper;
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
    assert (target != null);

    timeSlotRepository.deleteAll(target.getTimeSlots());
    target.setTimeSlots(
        new HashSet<>(
            timeSlotRepository.saveAll(
                () ->
                    source.getTimeSlots().stream()
                        .map(
                            timeSlot -> {
                              TimeSlot cloned =
                                  timeSlotRepository.save(
                                      timeSlot.toBuilder().id(0).rosterPlan(target).build());
                              Set<TimeSlotUserInfo> timeSlotUserInfos =
                                  new HashSet<>(
                                      timeSlotUserInfoRepository.saveAll(
                                          () ->
                                              timeSlot.getTimeSlotUserInfos().stream()
                                                  .map(
                                                      timeSlotUserInfo ->
                                                          timeSlotUserInfo.toBuilder()
                                                              .id(0)
                                                              .timeSlot(cloned)
                                                              .build())
                                                  .iterator()));
                              cloned.setTimeSlotUserInfos(timeSlotUserInfos);
                              return cloned;
                            })
                        .iterator())));

    rosterPlanUserInfoRepository.deleteAll(target.getRosterPlanUserInfos());
    target.setRosterPlanUserInfos(
        new HashSet<>(
            rosterPlanUserInfoRepository.saveAll(
                () ->
                    source.getRosterPlanUserInfos().stream()
                        .map(
                            rosterPlanUserInfo ->
                                rosterPlanUserInfo.toBuilder().id(0).rosterPlan(target).build())
                        .iterator())));
  }

  /**
   * Overwrites child info to parent
   * Attaching calls DB twice, which is not ideal even if cached
   * @return parent
   */
  public RosterPlan publish(RosterPlan child) {
    // Attach entity
    child = rosterPlanRepository.findById(child.getId()).orElseThrow();

    if (child.getParent() == null) {
      throw ExceptionHelper.from(
          child,
          RosterPlanPublishRequest.Fields.child,
          "rosterPlan.publish.does_not_have_a_parent");
    }

    // Attach entity
    RosterPlan parent = rosterPlanRepository.findById(child.getParent().getId()).orElseThrow();

    deepCopy(child, parent);
    return parent;
  }

  @Deprecated
  public RosterPlan justSave(RosterPlan rosterPlan) {
    return rosterPlanRepository.saveAll(Collections.singleton(rosterPlan)).get(0);
  }
}
