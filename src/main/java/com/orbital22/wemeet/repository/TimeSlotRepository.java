package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.TimeSlot;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "timeSlot", path = "timeSlot")
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
  @RestResource
  @Override
  @NonNull
  @PostAuthorize(
      "returnObject.isEmpty()"
          + "or hasPermission(returnObject.get(), 'READ')"
          + "or hasPermission(returnObject.get().getRosterPlan(), 'READ')")
  Optional<TimeSlot> findById(@NotNull Integer id);

  @RestResource
  @Override
  @NonNull
  // @PreAuthorize("hasPermission(#timeSlot.getRosterPlan(), 'WRITE')")
  <S extends TimeSlot> S save(@NonNull S timeSlot);
}
