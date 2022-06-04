package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.RosterPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "roster-plan", path = "roster-plan")
public interface RosterPlanRepository extends JpaRepository<RosterPlan, Integer> {}
