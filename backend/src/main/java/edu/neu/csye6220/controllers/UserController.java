package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.UserDAO;
import edu.neu.csye6220.exceptions.UserAlreadyExistsException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping
    public ResponseEntity<ResponseWrapper<Integer>> createUser(UserDAO userDAO, @Valid @RequestBody User user) {
        if(userDAO.userExists(user))
            throw new UserAlreadyExistsException("User already exists, please login.");
        int id = userDAO.createUser(user);
        return ResponseEntity.ok(new ResponseWrapper<>(2000, "Successfully create a user", id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>> getUser(UserDAO userDAO, @PathVariable int id) {
        if(id < 0)
            throw new IllegalArgumentException("Id is invalid!");
        Optional<User> optionalUser = userDAO.getUser(id);
        User u = optionalUser.orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        return ResponseEntity.ok(new ResponseWrapper<>(2000, "Successfully get a user by id", u));
    }
}
