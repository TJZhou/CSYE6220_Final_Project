package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye6220.models.enums.ExpenseType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "expense")
public class Expense implements Serializable {
    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @DecimalMin(value = "0", message = "Amount should greater than 0")
    @DecimalMax(value = "999999999999", message = "Amount should less than 1,000,000,000,000")
    private BigDecimal amount;

    @NotNull
    @Column(columnDefinition = "ENUM('Housing', 'Transportation', 'Food', 'Utilities', 'Clothing', 'Healthcare', 'Insurance', 'Debt', 'Education', 'Entertainment', 'Other')")
    @Enumerated(EnumType.STRING)
    private ExpenseType type;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

    @Size(max = 500)
    private String note;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
