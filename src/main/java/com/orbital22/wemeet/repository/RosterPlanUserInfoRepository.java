package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "rosterPlanUserInfo",
    path = "rosterPlanUserInfo")
public interface RosterPlanUserInfoRepository extends JpaRepository<RosterPlanUserInfo, Integer> {
  @RestResource
  @Override
  @NonNull
  @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
  Optional<RosterPlanUserInfo> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NonNull
  @PreAuthorize("#rosterPlanUserInfo.id == 0 or hasPermission(#rosterPlanUserInfo, 'WRITE')")
  <S extends RosterPlanUserInfo> S save(@NonNull S rosterPlanUserInfo);
}
