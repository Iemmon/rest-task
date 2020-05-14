package com.shop.exception;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException() {
        super();
    }

    public UserDoesNotExistException(String message, Throwable e) {
        super(message, e);
    }


}
