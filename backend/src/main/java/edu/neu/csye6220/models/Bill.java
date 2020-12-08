package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye6220.models.enums.ExpenseType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @JsonIgnore
    @JoinColumn(name = "user_contributor")
    public User userContributor;

    @NotNull
    @JsonIgnore
    @ManyToMany(mappedBy = "billsParticipated")
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
