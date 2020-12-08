package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye6220.utils.RegexUtil;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Pattern(regexp = RegexUtil.USERNAME_PATTERN, message = RegexUtil.INVALID_USERNAME)
    private String username;

    @NotBlank
    @Pattern(regexp = RegexUtil.PASSWORD_PATTERN, message = RegexUtil.INVALID_PASSWORD)
    private String password;

    @NotBlank
    @Email(message = RegexUtil.INVALID_EMAIL)
    private String email;

    @Pattern(regexp = RegexUtil.PHONE_PATTERN, message = RegexUtil.INVALID_PHONE)
    private String phone;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Income> incomes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Expense> expenses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupOwner", fetch = FetchType.LAZY)
    private Collection<BillGroup> groupOwned;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_user",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Collection<BillGroup> groupParticipated;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userContributor", fetch = FetchType.LAZY)
    private Collection<Bill> billsContributed;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_participated",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "bill_id") }
    )
    private Collection<Bill> billsParticipated;

    public User() { }

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(Collection<Income> incomes) {
        this.incomes = incomes;
    }

    public Collection<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Collection<Expense> expenses) {
        this.expenses = expenses;
    }

    public Collection<BillGroup> getGroupOwned() {
        return groupOwned;
    }

    public void setGroupOwned(Collection<BillGroup> groupOwned) {
        this.groupOwned = groupOwned;
    }

    public Collection<BillGroup> getGroupParticipated() {
        return groupParticipated;
    }

    public void setGroupParticipated(Collection<BillGroup> groupParticipated) {
        this.groupParticipated = groupParticipated;
    }

    public Collection<Bill> getBillsContributed() {
        return billsContributed;
    }

    public void setBillsContributed(Collection<Bill> billsContributed) {
        this.billsContributed = billsContributed;
    }

    public Collection<Bill> getBillsParticipated() {
        return billsParticipated;
    }

    public void setBillsParticipated(Collection<Bill> billsParticipated) {
        this.billsParticipated = billsParticipated;
    }
}
