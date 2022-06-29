package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "rosterPlan", path = "rosterPlan")
public interface RosterPlanRepository extends JpaRepository<RosterPlan, Integer> {
  @RestResource
  @Override
  @NonNull
  @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
  Optional<RosterPlan> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NonNull
  <S extends RosterPlan> S save(@NonNull S RosterPlan);
}
