package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
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
  @NotNull
  @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
  Optional<RosterPlan> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NotNull
  <S extends RosterPlan> S save(@NotNull S RosterPlan);

  @RestResource
  @Override
  void deleteById(@NotNull Integer id);

  @RestResource
  @Override
  void delete(@NotNull RosterPlan rosterPlan);
}
