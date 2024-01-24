package com.betheagent.betheagent.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameOrEmailAlreadyExistsException extends RuntimeException{

    public UsernameOrEmailAlreadyExistsException(String message){
        super(message);
    }
}
