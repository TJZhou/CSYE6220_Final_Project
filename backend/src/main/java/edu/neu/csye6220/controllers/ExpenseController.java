package edu.neu.csye6220.controllers;

import edu.neu.csye6220.dao.ExpenseDAO;
import edu.neu.csye6220.models.Expense;
import edu.neu.csye6220.models.ResponseWrapper;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.SumByType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequestMapping(value = "/expense")
@Valid
public class ExpenseController {

    private final ExpenseDAO expenseDAO;

    public ExpenseController(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    // get all expenses by userid and expense date
    @GetMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Collection<Expense>>>
    getExpenses(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestParam String date) {
        if(date.equals("All"))
            date = "";
        Collection<Expense> expenses = expenseDAO.getExpenses(userId, date);
        return ResponseEntity.ok(new ResponseWrapper<>(
                Status.GET_EXPENSES_SUCCESS.getCode(), Status.GET_EXPENSES_SUCCESS.getMsg(), expenses));
    }

    // get all expenses first and then group by category
    @GetMapping(value = "/overview/{userId}")
    public ResponseEntity<ResponseWrapper<Collection<SumByType>>>
    groupExpenseByCategory(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestParam String date) {
        if (date.equals("All"))
            date = "";
        Collection<SumByType> res = expenseDAO.groupExpensesByType(userId, date);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.GROUP_EXPENSES_SUCCESS.getCode(), Status.GROUP_EXPENSES_SUCCESS.getMsg(), res));
    }

    // create an expense
    @PostMapping(value = "/{userId}")
    public ResponseEntity<ResponseWrapper<Long>>
    addExpense(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long userId, @RequestBody Expense expense) {
        long id = expenseDAO.createExpense(userId, expense);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.CREATE_EXPENSE_SUCCESS.getCode(), Status.CREATE_EXPENSE_SUCCESS.getMsg(), id));
    }

    // update an expense
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Expense>>
    updateExpense(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id, @RequestBody Expense expense) {
        Expense newExpense = expenseDAO.updateExpense(id, expense);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.UPDATE_EXPENSE_SUCCESS.getCode(), Status.UPDATE_EXPENSE_SUCCESS.getMsg(), newExpense));
    }

    // delete an expense
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Void>>
    deleteExpense(@Min(1) @Max(Long.MAX_VALUE) @PathVariable long id) {
        expenseDAO.deleteExpense(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(Status.DELETE_EXPENSE_SUCCESS.getCode(), Status.DELETE_EXPENSE_SUCCESS.getMsg()));
    }
}
