package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.InvalidUserInfoException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.UserPassword;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserDAO extends DAO{

    public User checkLogin(String email, String password) {
        User u = getUserByEmail(email);
        if(!u.getPassword().equals(password))
            throw new InvalidUserInfoException(Status.INVALID_CREDENTIAL.getCode(), Status.INVALID_CREDENTIAL.getMsg());
        return u;
    }
    
    public User getUserByEmail(String email) {
        Query<User> query = getSession().createNativeQuery(QueryUtil.GET_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        try {
            begin();
            User user = query.uniqueResult();
            if(user != null) {
                commit();
                return user;
            } else
              throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public User getUserById(long id) {
        try {
            begin();
            User u = getSession().get(User.class, id);
            if(u != null) {
                commit();
                return u;
            } else
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
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
        User u = getUserById(id);
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
        User u = getUserById(id);
        if(!u.getPassword().equals(pswd.getOldPassword()))
            throw new InvalidUserInfoException(Status.INVALID_CREDENTIAL.getCode(), Status.INVALID_CREDENTIAL.getMsg());
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
