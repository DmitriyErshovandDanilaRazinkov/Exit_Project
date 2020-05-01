package com.exceptions;

public class NotFoundDataBaseException extends RuntimeException {

    public NotFoundDataBaseException(String message) {
        super(message);
    }
}
