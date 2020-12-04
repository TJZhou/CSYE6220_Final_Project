package edu.neu.csye6220.exceptions.handler;

import edu.neu.csye6220.exceptions.InvalidUserInfoException;
import edu.neu.csye6220.exceptions.UserAlreadyExistsException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserNotFoundException(UserNotFoundException ex) {
        System.out.println(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(Status.USER_NOT_FOUND.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        System.out.println(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(Status.USER_ALREADY_EXISTS.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidUserInfoException.class)
    public ResponseEntity<ResponseWrapper<String>> handleInvalidUserInfoException(InvalidUserInfoException ex) {
        System.out.println(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(Status.INVALID_CREDENTIAL.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        System.out.println(ex.getMessage());
        ResponseWrapper<String> rw = new ResponseWrapper<>(Status.CONSTRAINT_VIOLATION.getCode(), ex.getMessage());
        return new ResponseEntity<>(rw, HttpStatus.BAD_REQUEST);
    }
}
