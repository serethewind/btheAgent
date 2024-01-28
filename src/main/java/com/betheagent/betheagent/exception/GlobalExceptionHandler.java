package com.betheagent.betheagent.exception;

import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.ResourceNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.UsernameOrEmailAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> validationErrors = new HashMap<>();

        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.stream().map(objectError -> validationErrors.put(
                ((FieldError) objectError).getField()
                , objectError.getDefaultMessage()
        )).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception exception, WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapToErrorDetails(exception, "RESOURCE_NOT_FOUND", webRequest));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapToErrorDetails(exception, "INTERNAL_SERVER_ERROR", webRequest));
    }

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleUsernameOrEmailAlreadyExistsException(Exception exception, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mapToErrorDetails(exception, "CONFLICT", webRequest));
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResponseNotFoundException(Exception exception, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapToErrorDetails(exception, "PROPERTY_NOT_FOUND", webRequest));
    }

    private ErrorDetails mapToErrorDetails(Exception exception, String errorCodeMessage, WebRequest webRequest){
        return  ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode(errorCodeMessage)
                .build();
    }


}
