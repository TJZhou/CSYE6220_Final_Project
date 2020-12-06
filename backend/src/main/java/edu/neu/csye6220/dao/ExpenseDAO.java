package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.Expense;
import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.SumByType;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service(value = "ExpenseService")
public class ExpenseDAO extends DAO{

    public Expense getExpense(long id) {
        try {
            begin();
            Expense expense = getSession().get(Expense.class, id);
            if(expense != null) {
                commit();
                return expense;
            } else
                throw new EntryNotFoundException(Status.EXPENSE_NOT_FOUND.getCode(), Status.EXPENSE_NOT_FOUND.getMsg());
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Collection<Expense> getExpenses(long userId, String date) {
        Query<Expense> query = getSession().createNativeQuery(QueryUtil.GET_EXPENSES, Expense.class);
        query.setParameter("userId", userId);
        // pattern match
        query.setParameter("date", date + '%');
        try {
            begin();
            Collection<Expense> expenses = query.getResultList();
            commit();
            return expenses;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public long createExpense(long id, Expense expense) {
        try {
            begin();
            Session session = getSession();
            User user = session.get(User.class, id);
            if(user == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            expense.setUser(user);
            user.getExpenses().add(expense);
            session.update(user);
            commit();
            return expense.getId();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Expense updateExpense(long id, Expense newExpense) {
        Expense expense = getExpense(id);
        try {
            begin();
            expense.setAmount(newExpense.getAmount());
            expense.setDate(newExpense.getDate());
            expense.setType(newExpense.getType());
            expense.setNote(newExpense.getNote());
            getSession().update(expense);
            commit();
            return expense;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void deleteExpense(long id) {
        Expense expense = getExpense(id);
        try {
            begin();
            getSession().delete(expense);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Collection<SumByType> groupExpensesByType(long userId, String date) {
        Query<SumByType> query = getSession().createNativeQuery(QueryUtil.EXPENSES_GROUP_BY_TYPE, SumByType.class);
        query.setParameter("userId", userId);
        query.setParameter("date", date + '%');
        try {
            begin();
            Collection<SumByType> res = query.getResultList();
            commit();
            return res;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
