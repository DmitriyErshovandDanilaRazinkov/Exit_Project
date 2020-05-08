package com.exceptions;

public class DontHaveRightsException extends RuntimeException {

    public DontHaveRightsException(String message) {
        super(message);
    }
}

