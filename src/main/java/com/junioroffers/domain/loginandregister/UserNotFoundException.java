package com.junioroffers.domain.loginandregister;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String message) {
        super(message);
    }
}
