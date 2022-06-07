package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlanUserInfo;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "roster-plan-user-info",
    path = "roster-plan-user-info")
public interface RosterPlanUserInfoRepository extends JpaRepository<RosterPlanUserInfo, Integer> {
  @RestResource
  @Override
  @NonNull
  // @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
  Optional<RosterPlanUserInfo> findById(@NotNull Integer id);
}
