package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.GroupDAO;
import edu.neu.csye6220.exceptions.CustomIllegalArgumentException;
import edu.neu.csye6220.models.BillGroup;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/group")
@Valid
public class GroupController {

    private final GroupDAO groupDAO;

    public GroupController(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Collection<BillGroup>>>
    getGroups(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId) {
        Collection<BillGroup> billGroups = groupDAO.getGroups(userId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GET_GROUPS_SUCCESS.getCode(), Status.GET_GROUPS_SUCCESS.getMsg(), billGroups));
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

    @PutMapping(value = "/{userId}/{groupId}")
    public ResponseEntity<ResponseWrapper<Void>>
    updateGroupName(@PathVariable long userId, @PathVariable String groupId, @RequestBody Map<String, String> requestBody) {
        if(!requestBody.containsKey("name"))
            throw new CustomIllegalArgumentException(Status.INVALID_REQUEST_BODY.getCode(), Status.INVALID_CREDENTIAL.getMsg());
        groupDAO.updateGroupName(userId, groupId, requestBody.get("name"));
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_GROUP_SUCCESS.getCode(), Status.UPDATE_GROUP_SUCCESS.getMsg()));
    }

    @DeleteMapping(value = "/{userId}/{groupId}")
    public ResponseEntity<ResponseWrapper<Void>>
    deleteGroup(@PathVariable long userId, @PathVariable String groupId) {
        groupDAO.deleteGroup(userId, groupId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.DELETE_GROUP_SUCCESS.getCode(), Status.DELETE_GROUP_SUCCESS.getMsg()));
    }
}
