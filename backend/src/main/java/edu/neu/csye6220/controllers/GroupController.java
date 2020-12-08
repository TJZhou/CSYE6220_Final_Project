package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.GroupDAO;
import edu.neu.csye6220.models.BillGroup;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping(value = "/group")
@Valid
public class GroupController {

    private final GroupDAO groupDAO;

    public GroupController(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<String>>
    createGroup(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestBody BillGroup billGroup) {
        String groupId = groupDAO.createGroup(userId, billGroup);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_GROUP_SUCCESS.getCode(), Status.CREATE_GROUP_SUCCESS.getMsg(), groupId));
    }

    @PostMapping(value = "/{userId}/{groupId}")
    public ResponseEntity<ResponseWrapper<Void>>
    joinGroup(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @PathVariable String groupId) {
        groupDAO.joinGroup(userId, groupId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.JOIN_GROUP_SUCCESS.getCode(), Status.JOIN_GROUP_SUCCESS.getMsg()));
    }
}
