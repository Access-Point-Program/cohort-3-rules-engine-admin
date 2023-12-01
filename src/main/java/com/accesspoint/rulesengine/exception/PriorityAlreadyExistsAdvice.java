package com.accesspoint.rulesengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PriorityAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(PriorityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String priorityAlreadyExistsHandler(PriorityAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
