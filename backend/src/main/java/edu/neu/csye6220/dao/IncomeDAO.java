package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service(value = "incomeService")
public class IncomeDAO extends DAO{

    public Optional<Income> getIncome(long id) {
        Query<Income> query = getSession().createNativeQuery(QueryUtil.GET_INCOME, Income.class);
        query.setParameter("id", id);
        try {
            begin();
            Optional<Income> income = query.uniqueResultOptional();
            commit();
            return income;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Collection<Income> getIncomes(long userId, String date) {
        Query<Income> query = getSession().createNativeQuery(QueryUtil.GET_INCOMES, Income.class);
        query.setParameter("userId", userId);
        // pattern match
        query.setParameter("date", date + '%');
        try {
            begin();
            Collection<Income> incomes = query.getResultList();
            commit();
            return incomes;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public long createIncome(Income income) {
        try {
            begin();
            long incomeId = (long) getSession().save(income);
            commit();
            return incomeId;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Income updateIncome(long id, Income newIncome) {
        Optional<Income> optionalIncome = getIncome(id);
        Income income = optionalIncome.orElseThrow(() ->
                new EntryNotFoundException(Status.INCOME_NOT_FOUND.getCode(), Status.INCOME_NOT_FOUND.getMsg()));
        try {
            begin();
            income.setAmount(newIncome.getAmount());
            income.setDate(newIncome.getDate());
            income.setType(newIncome.getType());
            income.setNote(newIncome.getNote());
            getSession().update(income);
            commit();
            return income;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void deleteIncome(long id) {
        Optional<Income> optionalIncome = getIncome(id);
        Income income = optionalIncome.orElseThrow(() ->
                new EntryNotFoundException(Status.INCOME_NOT_FOUND.getCode(), Status.INCOME_NOT_FOUND.getMsg()));
        try {
            begin();
            getSession().delete(income);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
