package com.betheagent.betheagent.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserResourceNotFoundException extends RuntimeException {

    public UserResourceNotFoundException(String message){
        super(message);
    }
}
