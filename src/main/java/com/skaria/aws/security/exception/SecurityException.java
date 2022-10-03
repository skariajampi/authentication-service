package com.skaria.aws.security.exception;

import lombok.Getter;

@Getter
public class SecurityException extends RuntimeException {

    private String message;

    public SecurityException(String message) {
        super(message);
        this.message = message;
    }
}


