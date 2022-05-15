package com.orbital22.wemeet.exception;

import com.orbital22.wemeet.model.User;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(User user) {
        super(String.format("%s already exists!", user.getUsername()));
    }
}
