package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.UserDAO;
import edu.neu.csye6220.exceptions.UserAlreadyExistsException;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.UserPassword;
import edu.neu.csye6220.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // create a user
    @PostMapping
    public ResponseEntity<ResponseWrapper<Long>>
    createUser(@RequestBody User user) {
        if(userDAO.getUserByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException(Status.USER_ALREADY_EXISTS.getCode(), Status.USER_ALREADY_EXISTS.getMsg());
        long id = userDAO.createUser(user);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_USER_SUCCESS.getCode(), Status.CREATE_USER_SUCCESS.getMsg(), id));
    }

    // get user by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>>
    getUser(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id) {
        User u = userDAO.getUserById(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GET_USER_SUCCESS.getCode(), Status.GET_USER_SUCCESS.getMsg(), u));
    }

    // check login, get user by email first, and then compare user's password and input password
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseWrapper<String>>
    checkLogin(@RequestBody User user) {
        User u = userDAO.checkLogin(user.getEmail(), user.getPassword());
        String token = JwtUtil.createToken(u);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.LOGIN_SUCCESS.getCode(), Status.LOGIN_SUCCESS.getMsg(), token));
    }

    // update user's basic info
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>>
    updateUserInfo(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id, @RequestBody User user) {
        User u = userDAO.updateUserInfo(id, user);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_USER_INFO_SUCCESS.getCode(), Status.UPDATE_USER_INFO_SUCCESS.getMsg(), u));
    }

    // update user's password
    @PutMapping(value = "/pswd/{id}")
    public ResponseEntity<ResponseWrapper<User>>
    updatePassword(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id, @RequestBody UserPassword pswd) {
        User u = userDAO.updatePassword(id, pswd);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_USER_PSWD_SUCCESS.getCode(), Status.UPDATE_USER_PSWD_SUCCESS.getMsg(), u));
    }
}
