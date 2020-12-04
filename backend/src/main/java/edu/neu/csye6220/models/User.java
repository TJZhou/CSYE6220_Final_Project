package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.neu.csye6220.utils.RegexUtil;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Pattern(regexp = RegexUtil.USERNAME_PATTERN, message = RegexUtil.INVALID_USERNAME)
    private String username;

    @NotNull
    @Pattern(regexp = RegexUtil.PASSWORD_PATTERN, message = RegexUtil.INVALID_PASSWORD)
    private String password;

    @NotNull
    @Email(message = RegexUtil.INVALID_EMAIL)
    private String email;

    @Pattern(regexp = RegexUtil.PHONE_PATTERN, message = RegexUtil.INVALID_PHONE)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private Collection<Income> incomes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private Collection<Expense> expenses;

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
}
