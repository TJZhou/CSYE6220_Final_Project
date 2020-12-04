package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.UserDAO;
import edu.neu.csye6220.exceptions.UserAlreadyExistsException;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.UserPassword;
import edu.neu.csye6220.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Long>> createUser(@RequestBody User user) {
        if(userDAO.getUserByEmail(user.getEmail()) != null)
            throw new UserAlreadyExistsException(Status.USER_ALREADY_EXISTS.getCode(), Status.USER_ALREADY_EXISTS.getMsg());
        long id = userDAO.createUser(user);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_USER_SUCCESS.getCode(), Status.CREATE_USER_SUCCESS.getMsg(), id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>> getUser(@PathVariable long id) {
        if(id < 0)
            throw new IllegalArgumentException("Id is invalid!");
        User u = userDAO.getUserById(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GET_USER_SUCCESS.getCode(), Status.GET_USER_SUCCESS.getMsg(), u));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseWrapper<String>> checkLogin(@RequestBody User user) {
        User u = userDAO.checkLogin(user.getEmail(), user.getPassword());
        String token = JwtUtil.createToken(u);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.LOGIN_SUCCESS.getCode(), Status.LOGIN_SUCCESS.getMsg(), token));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<User>> updateUserInfo(@PathVariable long id, @RequestBody User user) {
        User u = userDAO.updateUserInfo(id, user);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_USER_INFO_SUCCESS.getCode(), Status.UPDATE_USER_INFO_SUCCESS.getMsg(), u));
    }

    @PutMapping(value = "/pswd/{id}")
    public ResponseEntity<ResponseWrapper<User>> updatePassword(@PathVariable long id, @RequestBody UserPassword pswd) {
        User u = userDAO.updatePassword(id, pswd);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_USER_PSWD_SUCCESS.getCode(), Status.UPDATE_USER_PSWD_SUCCESS.getMsg(), u));
    }
}
