package com.star.todolistserver.exceptions;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends CommonServerException{

    public TaskNotFoundException(String userId) {
        super(String.format("TaskId %s was not found!", userId));
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.NOT_FOUND;
    }
}
