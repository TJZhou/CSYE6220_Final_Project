package edu.neu.csye6220.dao;

import edu.neu.csye6220.exceptions.CustomIllegalArgumentException;
import edu.neu.csye6220.exceptions.EntryNotFoundException;
import edu.neu.csye6220.exceptions.UserNotFoundException;
import edu.neu.csye6220.models.Bill;
import edu.neu.csye6220.models.BillGroup;
import edu.neu.csye6220.models.User;
import edu.neu.csye6220.models.enums.Status;
import edu.neu.csye6220.utils.QueryUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service(value = "billService")
public class BillDAO extends DAO{

    public Collection<Bill> getBills(String groupId) {
        try {
            begin();
            Session session = getSession();
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            Collection<Bill> bills = billGroup.getBills();
            Hibernate.initialize(bills);
            for(Bill bill : bills)
                Hibernate.initialize(bill.getUserParticipants());
            commit();
            return bills;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public long createBill(long billContributorId, String groupId, Bill bill) {
        try {
            begin();
            Session session = getSession();
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            User contributor = session.get(User.class, billContributorId);
            if(contributor == null)
                throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
            if(!billGroup.getGroupParticipants().contains(contributor))
                throw new CustomIllegalArgumentException(Status.USER_NOT_IN_GROUP.getCode(), Status.USER_NOT_IN_GROUP.getMsg());
            billGroup.getBills().add(bill);
            bill.setBillGroup(billGroup);
            bill.setUserContributor(contributor);
            session.update(billGroup);
            commit();
            return bill.getId();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void updateBill(long billId, Bill newBill) {
        try {
            begin();
            Session session = getSession();
            Bill bill = session.get(Bill.class, billId);
            if(bill == null)
                throw new EntryNotFoundException(Status.BILL_NOT_FOUND.getCode(), Status.BILL_NOT_FOUND.getMsg());
            bill.setAmount(newBill.getAmount());
            bill.setNote(newBill.getNote());
            bill.setType(newBill.getType());
            bill.setCreatedAt(newBill.getCreatedAt());
            session.update(bill);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public void deleteBill(String groupId, long billId) {
        try {
            begin();
            Session session = getSession();
            BillGroup group = session.get(BillGroup.class, groupId);
            Bill bill = session.get(Bill.class, billId);
            if(group == null || bill == null)
                throw new EntryNotFoundException(Status.ENTRY_NOT_FOUND.getCode(), Status.ENTRY_NOT_FOUND.getMsg());
            if(!group.getBills().contains(bill))
                throw new EntryNotFoundException(Status.BILL_NOT_MATCH.getCode(), Status.BILL_NOT_MATCH.getMsg());
            Collection<User> participants = bill.getUserParticipants();
            for(User participant : participants)
                participant.getBillsParticipated().remove(bill);
            bill.getUserParticipants().clear();
            group.getBills().remove(bill);
            session.delete(bill);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }


    public void updateBillParticipants(String groupId, long billId, Collection<Long> participantsId) {
        try {
            begin();
            Session session = getSession();
            Bill bill = session.get(Bill.class, billId);
            if(bill == null)
                throw new EntryNotFoundException(Status.BILL_NOT_FOUND.getCode(), Status.BILL_NOT_FOUND.getMsg());
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            Collection<User> participants = bill.getUserParticipants();

            // delete previous user participated
            for(User participant : participants)
                participant.getBillsParticipated().remove(bill);
            participants.clear();

            // get all users by ids
            Query<User> query = session.createNativeQuery(QueryUtil.GET_USERS_BY_IDS, User.class);
            query.setParameterList("ids", participantsId);
            Collection<User> newParticipants = query.getResultList();

            // update new user participated
            for(User participant : newParticipants) {
                if(!billGroup.getGroupParticipants().contains(participant))
                    throw new CustomIllegalArgumentException(Status.USER_NOT_IN_GROUP.getCode(), Status.USER_NOT_IN_GROUP.getMsg());
                participant.getBillsParticipated().add(bill);
            }

            participants.addAll(newParticipants);
            session.update(bill);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    public Map<String, BigDecimal> splitAndCalculateBills(String groupId) {
        try {
            begin();
            Session session = getSession();
            BillGroup billGroup = session.get(BillGroup.class, groupId);
            if(billGroup == null)
                throw new EntryNotFoundException(Status.GROUP_NOT_FOUND.getCode(), Status.GROUP_NOT_FOUND.getMsg());
            // get all users in the bill group first
            Collection<User> users = billGroup.getGroupParticipants();
            // initialize amount of money as 0
            Map<User, BigDecimal> amountMap = new HashMap<>();
            for(User user : users)
                amountMap.put(user, new BigDecimal(0));
            Collection<Bill> bills = billGroup.getBills();
            // for each bill, calculate the bill user already paid or need to be paid
            for(Bill bill : bills) {
                BigDecimal amount = bill.getAmount();
                // for the bill contributor
                User contributor = bill.getUserContributor();
                if(contributor == null)
                    throw new UserNotFoundException(Status.USER_NOT_FOUND.getCode(), Status.USER_NOT_FOUND.getMsg());
                amountMap.put(contributor, amountMap.get(contributor).add(amount));
                // for the bill participants
                Collection<User> userParticipants = bill.getUserParticipants();
                for(User participant: userParticipants)
                    amountMap.put(participant, amountMap.get(participant).subtract(
                            amount.divide(new BigDecimal(userParticipants.size()), 2, RoundingMode.HALF_UP)));
            }
            int uniqueId = 0;
            Map<String, BigDecimal> formatMap = new HashMap<>();
            for(Map.Entry<User, BigDecimal> e : amountMap.entrySet())
                formatMap.put("User" + (uniqueId++) + ":" +e.getKey().getUsername(), e.getValue());
            return formatMap;
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }
}
