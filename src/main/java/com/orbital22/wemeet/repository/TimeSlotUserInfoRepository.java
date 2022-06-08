package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.TimeSlotUserInfo;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(
        collectionResourceRel = "time-slot-user-info",
        path = "time-slot-user-info")
public interface TimeSlotUserInfoRepository extends JpaRepository<TimeSlotUserInfo, Integer> {
    @RestResource
    @Override
    @NonNull
        // @PostAuthorize("returnObject.isEmpty() or hasPermission(returnObject.get(), 'READ')")
    Optional<TimeSlotUserInfo> findById(@NotNull Integer id);
}
