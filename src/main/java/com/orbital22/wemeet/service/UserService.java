package com.orbital22.wemeet.service;

import com.orbital22.wemeet.exception.UserAlreadyExistsException;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public void register(User user) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user);
        }
        userRepository.save(user);
    }
}
