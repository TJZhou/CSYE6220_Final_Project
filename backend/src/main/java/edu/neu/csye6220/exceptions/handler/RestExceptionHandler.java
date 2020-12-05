package edu.neu.csye6220.exceptions.handler;

import edu.neu.csye6220.exceptions.*;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserNotFoundException(UserNotFoundException ex) {
        logger.warn(ex.getCode() + "----" + ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.warn(ex.getCode() + "----" + ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidUserInfoException.class)
    public ResponseEntity<ResponseWrapper<String>> handleInvalidUserInfoException(InvalidUserInfoException ex) {
        logger.warn(ex.getCode() + "----" + ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntryNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> handleEntryNotFoundException(EntryNotFoundException ex) {
        logger.warn(ex.getCode() + "----" + ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.warn(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(Status.CONSTRAINT_VIOLATION.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }
}
