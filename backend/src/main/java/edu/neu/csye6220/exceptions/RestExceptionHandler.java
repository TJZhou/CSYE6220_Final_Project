package edu.neu.csye6220.exceptions;

import org.slf4j.Logger;
import edu.neu.csye6220.models.ResponseWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(4010, ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.warn(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(4011, ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

}
