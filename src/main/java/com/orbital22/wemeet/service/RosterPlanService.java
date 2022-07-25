package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanPostRequest;
import com.orbital22.wemeet.dto.RosterPlanPublishRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.util.ExceptionHelper;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private final RosterPlanRepository rosterPlanRepository;
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private final TimeSlotRepository timeSlotRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;

  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @PersistenceContext EntityManager entityManager;

  public void deepCopy(@NotNull RosterPlan source, @NotNull RosterPlan target) {
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

    target.setMinAllocationCount(source.getMinAllocationCount());
    target.setMaxAllocationCount(source.getMaxAllocationCount());
  }

  /*
   * Overwrites child info to parent
   * Attaching calls DB twice, which is not ideal even if cached
   */
  public void publish(RosterPlan child) {
    RosterPlan parent = child.getParent();
    if (parent == null) {
      throw ExceptionHelper.from(
          child,
          RosterPlanPublishRequest.Fields.child,
          "rosterPlan.publish.does_not_have_a_parent");
    }

    try (Session session = entityManager.unwrap(Session.class)) {
      session.update(child);
      session.update(parent);
      deepCopy(child, parent);
    }
  }

  // TODO consider splitting RosterPlan into Meeting and Solution
  // TODO consider making `owner` not null
  @PreAuthorize("isAuthenticated()")
  public RosterPlan post(RosterPlanPostRequest request) {
    User owner = request.getOwner();
    if (owner == null) {
      owner = userService.me().orElseThrow();
    }

    // TODO to be refactored through splitting
    Boolean solved = request.getParent() != null ? Boolean.FALSE : null;
    int minAllocationCount =
        Optional.ofNullable(request.getMinAllocationCount())
            .or(
                () ->
                    Optional.ofNullable(request.getParent()).map(RosterPlan::getMinAllocationCount))
            .orElse(1);
    int maxAllocationCount =
        Optional.ofNullable(request.getMinAllocationCount())
            .or(
                () ->
                    Optional.ofNullable(request.getParent()).map(RosterPlan::getMaxAllocationCount))
            .orElse(1);

    RosterPlan rosterPlan =
        rosterPlanRepository.save(
            RosterPlan.builder()
                .title(request.getTitle())
                .parent(request.getParent())
                .owner(owner)
                .solved(solved)
                .minAllocationCount(minAllocationCount)
                .maxAllocationCount(maxAllocationCount)
                .build());
    aclRegisterService.register(rosterPlan, rosterPlan.getOwner().getEmail(), READ, WRITE, DELETE);

    return rosterPlan;
  }
}
