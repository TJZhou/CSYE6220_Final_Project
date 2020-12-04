package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.IncomeDAO;
import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/income")
public class IncomeController {

    private final IncomeDAO incomeDAO;

    public IncomeController(IncomeDAO incomeDAO) {
        this.incomeDAO = incomeDAO;
    }

    // get all incomes by userid and income date
    @GetMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Collection<Income>>>
        getIncomes(@PathVariable long userId, @RequestParam String date) {
        if(date.equals("All"))
            date = "";
        Collection<Income> incomes = incomeDAO.getIncomes(userId, date);
        return ResponseEntity.ok(new ResponseWrapper<>(
                Status.GET_INCOMES_SUCCESS.getCode(), Status.GET_INCOMES_SUCCESS.getMsg(), incomes));
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Long>> addIncome(@PathVariable long userId, @RequestBody Income income) {
        long id = incomeDAO.createIncome(userId, income);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_INCOME_SUCCESS.getCode(), Status.CREATE_INCOME_SUCCESS.getMsg(), id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Income>> updateIncome(@PathVariable long id, @RequestBody  Income income) {
        Income newIncome = incomeDAO.updateIncome(id, income);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_INCOME_SUCCESS.getCode(), Status.UPDATE_INCOME_SUCCESS.getMsg(), newIncome));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteIncome(@PathVariable long id) {
        incomeDAO.deleteIncome(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.DELETE_INCOME_SUCCESS.getCode(), Status.DELETE_INCOME_SUCCESS.getMsg()));
    }
}
