package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.InvalidUserInfoException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.UserPassword;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
public class UserDAO extends DAO{

    public User checkLogin(String email, String password) {
        Optional<User> user = getUserByEmail(email);
        User u = user.orElseThrow(() -> new UserNotFoundException(Status.USER_NOT_FOUND.getMsg()));
        if(!u.getPassword().equals(password))
            throw new InvalidUserInfoException(Status.INVALID_CREDENTIAL.getMsg());
        return u;
    }
    
    public Optional<User> getUserByEmail(String email) {
        Query<User> query = getSession().createNativeQuery(QueryUtil.GET_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        try {
            begin();
            Optional<User> optionalUser = query.uniqueResultOptional();
            commit();
            return optionalUser;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Optional<User> getUserById(long id) {
        Query<User> query = getSession().createNativeQuery(QueryUtil.GET_USER_BY_ID, User.class);
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

    public long createUser(User u) {
        try {
            begin();
            long userId = (long) getSession().save(u);
            commit();
            return userId;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public User updateUserInfo(long id, User newU) {
        Optional<User> user = getUserById(id);
        User u = user.orElseThrow(() -> new UserNotFoundException(Status.USER_NOT_FOUND.getMsg()));
        try {
            begin();
            u.setUsername(newU.getUsername());
            u.setPhone(newU.getPhone());
            u.setEmail(newU.getEmail());
            getSession().update(u);
            commit();
            return u;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public User updatePassword(long id, UserPassword pswd) {
        Optional<User> user = getUserById(id);
        User u = user.orElseThrow(() -> new UserNotFoundException(Status.USER_NOT_FOUND.getMsg()));
        if(!u.getPassword().equals(pswd.getOldPassword()))
            throw new InvalidUserInfoException(Status.INVALID_CREDENTIAL.getMsg());
        try {
            begin();
            u.setPassword(pswd.getNewPassword());
            getSession().update(u);
            commit();
            return u;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
