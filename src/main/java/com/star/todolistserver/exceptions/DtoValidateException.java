package com.star.todolistserver.exceptions;

import org.springframework.http.HttpStatus;

public class DtoValidateException extends CommonServerException {

    public DtoValidateException(String fieldName) {
        super(String.format("Field %s was not found or has NULL value!", fieldName));
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
