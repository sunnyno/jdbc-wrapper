package com.dzytsiuk.jdbcwrapper.exception;

public class MoreThanOneObjectFoundException extends RuntimeException {
    public MoreThanOneObjectFoundException(String message) {
        super(message);
    }
}
