package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @RestResource(exported = false)
    @Override
    <S extends User> @NotNull S save(@NotNull S entity);

    @Secured("ROLE_ADMIN")
    @Override
    void deleteById(@NotNull Integer integer);

    @Secured("ROLE_ADMIN")
    @Override
    void delete(@NotNull User user);

    @Secured("ROLE_ADMIN")
    @Override
    void deleteAll(@NotNull Iterable<? extends User> users);

    @Secured("ROLE_ADMIN")
    @Override
    void deleteAll();

    @Secured("ROLE_ADMIN")
    @Override
    @NotNull
    List<User> findAll(@NotNull Sort sort);

    @Secured("ROLE_ADMIN")
    @Override
    @NotNull
    Page<User> findAll(@NotNull Pageable pageable);

    @Secured("ROLE_ADMIN")
    @Override
    @NotNull
    Optional<User> findById(@NotNull Integer integer);

    @Secured("ROLE_ADMIN")
    @Override
    @NotNull
    List<User> findAll();
}
