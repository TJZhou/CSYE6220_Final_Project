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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapper<Collection<Income>>>
        getIncomes(@PathVariable long id, @RequestParam String date) {
        if(date.equals("All"))
            date = "";
        Collection<Income> incomes = incomeDAO.getIncomes(id, date);
        return ResponseEntity.ok(new ResponseWrapper<>(
                Status.GET_INCOMES_SUCCESS.getCode(), Status.GET_INCOMES_SUCCESS.getMsg(), incomes));
    }
}
