package com.star.todolistserver.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CommonServerException extends RuntimeException{
    public abstract HttpStatus getErrorCode();

    public CommonServerException() {
    }

    public CommonServerException(String message) {
        super(message);
    }

    public CommonServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonServerException(Throwable cause) {
        super(cause);
    }

    public CommonServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
