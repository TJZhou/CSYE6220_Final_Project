package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.IncomeDAO;
import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Validated
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
    getIncomes(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestParam String date) {
        if(date.equals("All"))
            date = "";
        Collection<Income> incomes = incomeDAO.getIncomes(userId, date);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GET_INCOMES_SUCCESS.getCode(), Status.GET_INCOMES_SUCCESS.getMsg(), incomes));
    }

    // get all incomes first and then group by category
    @GetMapping(value = "/overview/{userId}")
    public ResponseEntity<ResponseWrapper<Map<String, Collection<Income>>>>
    groupIncomesByCategory(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestParam String date) {
        if(date.equals("All"))
            date = "";
        Collection<Income> incomes = incomeDAO.getIncomes(userId, date);
        Map<String, Collection<Income>> incomesMap = new HashMap<>();
        for(Income income : incomes) {
            incomesMap.putIfAbsent(income.getType().toString(), new ArrayList<>());
            incomesMap.get(income.getType().toString()).add(income);
        }
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GROUP_INCOMES_SUCCESS.getCode(), Status.GROUP_INCOMES_SUCCESS.getMsg(), incomesMap));
    }

    // create an income
    @PostMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Long>>
    addIncome(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestBody Income income) {
        long id = incomeDAO.createIncome(userId, income);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_INCOME_SUCCESS.getCode(), Status.CREATE_INCOME_SUCCESS.getMsg(), id));
    }

    // update an income 
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Income>>
    updateIncome(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id, @RequestBody  Income income) {
        Income newIncome = incomeDAO.updateIncome(id, income);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_INCOME_SUCCESS.getCode(), Status.UPDATE_INCOME_SUCCESS.getMsg(), newIncome));
    }

    // delete an income
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Void>>
    deleteIncome(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id) {
        incomeDAO.deleteIncome(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.DELETE_INCOME_SUCCESS.getCode(), Status.DELETE_INCOME_SUCCESS.getMsg()));
    }
}
