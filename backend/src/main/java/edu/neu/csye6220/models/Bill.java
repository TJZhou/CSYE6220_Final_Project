package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye6220.models.enums.ExpenseType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    public BillGroup billGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_contributor")
    public User userContributor;

    @ManyToMany(mappedBy = "billsParticipated")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<User> userParticipants;

    @NotNull
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    public Date createdAt;

    @NotNull
    @Column(columnDefinition = "ENUM('Housing', 'Transportation', 'Food', 'Utilities', 'Clothing', 'Healthcare', " +
            "'Insurance', 'Debt', 'Education', 'Entertainment', 'Other')")
    @Enumerated(EnumType.STRING)
    private ExpenseType type;

    @NotNull
    @DecimalMin(value = "0", message = "Amount should greater than 0")
    @DecimalMax(value = "999999999999", message = "Amount should less than 1,000,000,000,000")
    private BigDecimal amount;

    @NotNull
    @Size(max = 500)
    private String note;

    public Bill() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BillGroup getBillGroup() {
        return billGroup;
    }

    public void setBillGroup(BillGroup billGroup) {
        this.billGroup = billGroup;
    }

    public User getUserContributor() {
        return userContributor;
    }

    public void setUserContributor(User userContributor) {
        this.userContributor = userContributor;
    }

    public Collection<User> getUserParticipants() {
        return userParticipants;
    }

    public void setUserParticipants(Collection<User> userParticipants) {
        this.userParticipants = userParticipants;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
