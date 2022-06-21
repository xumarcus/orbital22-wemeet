package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/*
 * Permissions used: WRITE | DELETE
 * use saveAll(Collections.singleton()) to ignore UserCreateAspect
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
  @RestResource
  @NotNull
  Optional<User> findByEmail(String email);

  @RestResource
  @Override
  @NotNull
  @PreAuthorize("isAuthenticated()")
  Optional<User> findById(@NonNull Integer id);

  @RestResource
  @Override
  @NotNull
  @PreAuthorize("#user.id == 0 or hasPermission(#user, 'WRITE')")
  <S extends User> S save(@NonNull S user);

  default <S extends User> S justSave(@NonNull S user) {
    return this.save(user);
  }
}
