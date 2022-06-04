package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "time-slot", path = "time-slot")
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {}
