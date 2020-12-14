package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.CustomIllegalArgumentException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.models.pojos.UserPassword;
import edu.neu.csye6220.utils.EncryptionUtil;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
public class UserDAO extends DAO{

    private final EncryptionUtil encryptionUtil;

    public UserDAO(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    public User checkLogin(String email, String password) {
        Optional<User> user = getUserByEmail(email);
        User u = user.orElseThrow(() ->
                new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg()));
        if(!encryptionUtil.decrypt(u.getPassword()).equals(password))
            throw new CustomIllegalArgumentException(Status.INVALID_CREDENTIAL.getCode(), Status.INVALID_CREDENTIAL.getMsg());
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
            u.setPassword(encryptionUtil.encrypt(u.getPassword()));
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
        if(!encryptionUtil.decrypt(u.getPassword()).equals(pswd.getOldPassword()))
            throw new CustomIllegalArgumentException(Status.INVALID_CREDENTIAL.getCode(), Status.INVALID_CREDENTIAL.getMsg());
        try {
            begin();
            u.setPassword(encryptionUtil.encrypt(pswd.getNewPassword()));
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
