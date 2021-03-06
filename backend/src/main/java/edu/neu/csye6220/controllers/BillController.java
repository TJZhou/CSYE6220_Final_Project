package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.BillDAO;
import edu.neu.csye6220.exceptions.CustomIllegalArgumentException;
import edu.neu.csye6220.models.Bill;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/bill")
@Valid
public class BillController {
    private final BillDAO billDAO;

    public BillController(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    // create a bill within a group
    @PostMapping("/{billContributorId}/{groupId}")
    public ResponseEntity<ResponseWrapper<Long>>
    createBill(@PathVariable long billContributorId, @PathVariable String groupId, @RequestBody Bill bill) {
        long billId = billDAO.createBill(billContributorId, groupId, bill);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_BILL_SUCCESS.getCode(), Status.CREATE_BILL_SUCCESS.getMsg(), billId));
    }

    // get all bills within a group
    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseWrapper<Collection<Bill>>>
    getBills(@PathVariable String groupId) {
        Collection<Bill> bills = billDAO.getBills(groupId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GET_BILLS_SUCCESS.getCode(), Status.GET_BILLS_SUCCESS.getMsg(), bills));
    }

    // update a certain bill by billId
    @PutMapping("/{billId}")
    public ResponseEntity<ResponseWrapper<Void>>
    updateBill(@PathVariable long billId, @RequestBody Bill bill) {
        billDAO.updateBill(billId, bill);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_BILL_INFO_SUCCESS.getCode(), Status.UPDATE_BILL_INFO_SUCCESS.getMsg()));
    }
    
    @PutMapping("/{groupId}/{billId}")
    public ResponseEntity<ResponseWrapper<Void>>
    updateBillParticipants(@PathVariable String groupId, @PathVariable long billId, @RequestBody Map<String, Collection<Long>> requestBody) {
        if(!requestBody.containsKey("participantsId"))
            throw new CustomIllegalArgumentException(Status.INVALID_REQUEST_BODY.getCode(), Status.INVALID_REQUEST_BODY.getMsg());
        billDAO.updateBillParticipants(groupId, billId, requestBody.get("participantsId"));
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_BILL_PARTICIPANTS_SUCCESS.getCode(), Status.UPDATE_BILL_PARTICIPANTS_SUCCESS.getMsg()));
    }

    @DeleteMapping("/{groupId}/{billId}")
    public ResponseEntity<ResponseWrapper<Void>>
    deleteBill( @PathVariable String groupId, @PathVariable long billId) {
        billDAO.deleteBill(groupId, billId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.DELETE_BILL_SUCCESS.getCode(), Status.DELETE_BILL_SUCCESS.getMsg()));
    }

    /**
     * Deprecated: the calculation logic will be handled in the frontend
     */
    @Deprecated
    @GetMapping("/calc/{groupId}")
    public ResponseEntity<ResponseWrapper<Map<String, BigDecimal>>>
    splitAndCalculateBills(@PathVariable String groupId) {
        Map<String, BigDecimal> res = billDAO.splitAndCalculateBills(groupId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CALC_BILL_SUCCESS.getCode(), Status.CALC_BILL_SUCCESS.getMsg(), res));
    }
}