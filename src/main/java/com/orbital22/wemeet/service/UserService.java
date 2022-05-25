package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public void register(User user) {
        Assert.isTrue(!userRepository.existsByEmail(user.getEmail()), "Email already exists?");
        userRepository.save(user);
    }

    /*
    User anonymous(String email) {
        return User.builder()
                .email(email)
                .password(null) // FIXME
                .enabled(true)
                .registered(false)
                .build();
    }
     */
}
