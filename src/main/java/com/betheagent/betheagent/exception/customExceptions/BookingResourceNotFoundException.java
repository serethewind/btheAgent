package com.betheagent.betheagent.exception.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingResourceNotFoundException extends RuntimeException{

    public BookingResourceNotFoundException(String message){
        super(message);
    }
}
