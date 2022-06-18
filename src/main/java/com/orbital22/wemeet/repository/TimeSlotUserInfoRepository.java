package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "timeSlotUserInfo", path = "timeSlotUserInfo")
public interface TimeSlotUserInfoRepository extends JpaRepository<TimeSlotUserInfo, Integer> {
  @RestResource
  @Override
  @NonNull
  @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
  Optional<TimeSlotUserInfo> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NonNull
  @PreAuthorize("#timeSlotUserInfo.id == 0 or hasPermission(#timeSlotUserInfo, 'WRITE')")
  <S extends TimeSlotUserInfo> S save(@NonNull S timeSlotUserInfo);
}
