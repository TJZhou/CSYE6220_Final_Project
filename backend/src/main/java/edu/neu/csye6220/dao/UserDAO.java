package edu.neu.csye6220.dao;

import edu.neu.csye6220.models.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
public class UserDAO extends DAO{
    
    public boolean userExists(User u) {
        String sql = "select * from User where email=:email";
        Query<User> query = getSession().createNativeQuery(sql, User.class);
        query.setParameter("email", u.getEmail());
        try {
            begin();
            Optional<User> optionalUser = query.uniqueResultOptional();
            commit();
            return optionalUser.isPresent();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Optional<User> getUser(int id) {
        String sql = "select * from User where user_id =:id";
        Query<User> query = getSession().createNativeQuery(sql, User.class);
        query.setParameter("id", id);
        Optional<User> u;
        try {
            begin();
            u = query.uniqueResultOptional();
            commit();
            return u;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public int createUser(User u) {
        try {
            begin();
            int userId = (Integer) getSession().save(u);
            commit();
            return userId;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
