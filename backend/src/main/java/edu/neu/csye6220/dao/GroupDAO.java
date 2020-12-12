package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.CustomIllegalArgumentException;
import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.Bill;
import edu.neu.csye6220.models.BillGroup;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
            // the owner of this group must also be the participants of this group
            billGroup.setGroupParticipants(new ArrayList<>());
            billGroup.getGroupParticipants().add(user);
            session.save(billGroup);
            user.getGroupParticipated().add(billGroup);
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
            if(user.getGroupParticipated().contains(billGroup))
                throw new CustomIllegalArgumentException(Status.GROUP_ALREADY_JOINED.getCode(), Status.GROUP_ALREADY_JOINED.getMsg());
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

    public Collection<BillGroup> getGroups(long userId) {
        try {
            begin();
            Session session = getSession();
            User user = session.get(User.class ,userId);
            if(user == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            Collection<BillGroup> billGroups = user.getGroupParticipated();
            Hibernate.initialize(billGroups);
            for(BillGroup billGroup : billGroups)
                Hibernate.initialize(billGroup.getGroupParticipants());
            commit();
            return billGroups;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void updateGroupName(long userId, String groupId, String groupName) {
        try {
            begin();
            Session session = getSession();
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            if(billGroup.getGroupOwner().getId() != userId)
                throw new CustomIllegalArgumentException(Status.INVALID_GROUP_CHANGE_PERMIT.getCode(), Status.INVALID_GROUP_CHANGE_PERMIT.getMsg());
            billGroup.setGroupName(groupName);
            session.update(billGroup);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void deleteGroup(long userId, String groupId) {
        try {
            begin();
            Session session = getSession();
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            if(billGroup.getGroupOwner().getId() != userId)
                throw new CustomIllegalArgumentException(Status.INVALID_GROUP_CHANGE_PERMIT.getCode(), Status.INVALID_GROUP_CHANGE_PERMIT.getMsg());
            // remove all corresponding bills
            Collection<Bill> bills = billGroup.getBills();
            for(Bill bill : bills) {
                Collection<User> billParticipants = bill.getUserParticipants();
                for(User participants : billParticipants)
                    participants.getBillsParticipated().remove(bill);
                billParticipants.clear();
                getSession().delete(bill);
            }
            bills.clear();

            // remove all corresponding users from the billGroup
            Collection<User> groupUsers = billGroup.getGroupParticipants();
            for(User user : groupUsers)
                user.getGroupParticipated().remove(billGroup);
            groupUsers.clear();

            getSession().delete(billGroup);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
