package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.model.User;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "timeSlotUserInfo", path = "timeSlotUserInfo")
public interface TimeSlotUserInfoRepository extends JpaRepository<TimeSlotUserInfo, Integer> {
  @Query(
      "SELECT I FROM TimeSlotUserInfo I"
          + " WHERE I.timeSlot.rosterPlan = :rosterPlan"
          + " AND I.user = :user")
  Collection<TimeSlotUserInfo> findByRosterPlanAndUser(RosterPlan rosterPlan, User user);

  Optional<TimeSlotUserInfo> findByTimeSlotAndUser(TimeSlot timeSlot, User user);

  @RestResource
  @Override
  @NonNull
  @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
  Optional<TimeSlotUserInfo> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NonNull
  // @PreAuthorize("#timeSlotUserInfo.id == 0 or hasPermission(#timeSlotUserInfo, 'WRITE')")
  <S extends TimeSlotUserInfo> S save(@NonNull S timeSlotUserInfo);
}
