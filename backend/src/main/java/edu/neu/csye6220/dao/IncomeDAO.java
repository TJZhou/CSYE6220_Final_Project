package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service(value = "incomeService")
public class IncomeDAO extends DAO{

    public Income getIncome(long id) {
        try {
            begin();
            Income income = getSession().get(Income.class, id);
            if(income != null) {
                commit();
                return income;
            } else
                throw new EntryNotFoundException(Status.INCOME_NOT_FOUND.getCode(), Status.INCOME_NOT_FOUND.getMsg());
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

    public long createIncome(long id, Income income) {
        try {
            begin();
            Session session = getSession();
            User user = session.get(User.class, id);
            if(user == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            income.setUser(user);
            user.getIncomes().add(income);
            session.update(user);
            commit();
            return income.getId();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Income updateIncome(long id, Income newIncome) {
        Income income = getIncome(id);
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
        Income income = getIncome(id);
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
