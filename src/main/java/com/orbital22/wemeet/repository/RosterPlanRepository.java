package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "rosterPlan", path = "rosterPlan")
public interface RosterPlanRepository extends JpaRepository<RosterPlan, Integer> {
  @RestResource
  @Override
  @NotNull
  // @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
  Optional<RosterPlan> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NotNull
  <S extends RosterPlan> S save(@NotNull S RosterPlan);

  @RestResource
  @NotNull
  Collection<RosterPlan> findByParent(RosterPlan parent);

  @RestResource
  @NotNull
  Collection<RosterPlan> findByParentIsNullAndOwner(User owner);

  @RestResource
  @Override
  void deleteById(@NotNull Integer id);

  @RestResource
  @Override
  void delete(@NotNull RosterPlan rosterPlan);
}
