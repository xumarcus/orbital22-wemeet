package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "roster-plan", path = "roster-plan")
public interface RosterPlanRepository extends JpaRepository<RosterPlan, Integer> {
  @RestResource
  @Override
  @NonNull
  // @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
  Optional<RosterPlan> findById(@NotNull Integer id);
}
