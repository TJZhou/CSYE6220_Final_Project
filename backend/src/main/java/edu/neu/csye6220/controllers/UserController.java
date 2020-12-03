package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.UserDAO;
import edu.neu.csye6220.exceptions.UserAlreadyExistsException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.pojos.UserPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Long>> createUser(@RequestBody User user) {
        if(userDAO.getUserByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("User already exists, please login.");
        long id = userDAO.createUser(user);
        return ResponseEntity.ok(new ResponseWrapper<>(2000, "Successfully create a user", id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>> getUser(@PathVariable long id) {
        if(id < 0)
            throw new IllegalArgumentException("Id is invalid!");
        Optional<User> optionalUser = userDAO.getUserById(id);
        User u = optionalUser.orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        return ResponseEntity.ok(new ResponseWrapper<>(2001, "Successfully get a user by id", u));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<User>> checkLogin(@RequestBody User user) {
        User u = userDAO.checkLogin(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(new ResponseWrapper<>(2002, "Login Successfully", u));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>> updateUserInfo(@PathVariable long id, @RequestBody User user) {
        User u = userDAO.updateUserInfo(id, user);
        return ResponseEntity.ok(new ResponseWrapper<>(2003, "Successfully update user information", u));
    }

    @PutMapping(value = "/pswd/{id}")
    public ResponseEntity<ResponseWrapper<User>> updatePassword(@PathVariable long id, @RequestBody UserPassword pswd) {
        User u = userDAO.updatePassword(id, pswd);
        return ResponseEntity.ok(new ResponseWrapper<>(2003, "Successfully update user information", u));
    }
}
