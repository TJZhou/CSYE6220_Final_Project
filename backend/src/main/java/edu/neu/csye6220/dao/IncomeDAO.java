package edu.neu.csye6220.dao;

import edu.neu.csye6220.models.Income;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service(value = "incomeService")
public class IncomeDAO extends DAO{
    public Collection<Income> getIncomes(long id, String date) {
        Query<Income> query = getSession().createNativeQuery(QueryUtil.GET_INCOME, Income.class);
        query.setParameter("id", id);
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
}
