package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public void register(User user) {
        Assert.isTrue(!userRepository.existsByEmail(user.getEmail()), "Email already exists?");
        userRepository.save(user);
    }
}
