package com.star.todolistserver.web;


import com.star.todolistserver.exceptions.CommonServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(CommonServerException.class)
    public ResponseEntity<CommonError> buildKnownError(CommonServerException cse) {
        log.error("error happened. ", cse);
        return buildResponse(cse.getErrorCode(), cse.getMessage());
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CommonError> buildUnKnownError(Throwable t) {
        log.error("error happened. ", t);
        return buildResponse(HttpStatus.BAD_REQUEST, t.getMessage());
    }

    private ResponseEntity<CommonError> buildResponse(HttpStatus code, String message) {
        return ResponseEntity.status(code).body(new CommonError(message, code.value()));
    }

}
