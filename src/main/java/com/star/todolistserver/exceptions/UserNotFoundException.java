package com.star.todolistserver.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CommonServerException{

    public UserNotFoundException(String userId) {
        super(String.format("UserId %s was not found!", userId));
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.NOT_FOUND;
    }
}
