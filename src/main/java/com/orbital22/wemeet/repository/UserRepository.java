package com.orbital22.wemeet.repository;

import com.orbital22.wemeet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean exists(User user);
    User findByUsername(String username);
}
