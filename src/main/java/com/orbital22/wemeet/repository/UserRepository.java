package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

  @RestResource
  Optional<User> findByEmail(String email);

    @RestResource
    @Override
    @NotNull
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Optional<User> findById(@NotNull Integer id);
}
