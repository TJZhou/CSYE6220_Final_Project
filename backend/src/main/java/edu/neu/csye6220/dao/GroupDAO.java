package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.Bill;
import edu.neu.csye6220.models.BillGroup;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service(value = "groupService")
public class GroupDAO extends DAO{

    /**
     * create a group which belongs to {userId}
     * {userId} is at the same time participants of this group
     */
    public String createGroup(long userId, BillGroup billGroup) {
        try {
            begin();
            Session session = getSession();
            User user = session.get(User.class, userId);
            if(user == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            billGroup.setId(UUID.randomUUID().toString());
            billGroup.setGroupOwner(user);
            user.getGroupOwned().add(billGroup);
            // the owner of this group must also be the participants of this group
            user.getGroupParticipated().add(billGroup);
            billGroup.setGroupParticipants(new ArrayList<>());
            billGroup.getGroupParticipants().add(user);
            session.update(user);
            commit();
            return billGroup.getId();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void joinGroup(long userId, String groupId) {
        try {
            begin();
            Session session = getSession();
            User user = session.get(User.class, userId);
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(user == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            user.getGroupParticipated().add(billGroup);
            billGroup.getGroupParticipants().add(user);
            session.update(user);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
