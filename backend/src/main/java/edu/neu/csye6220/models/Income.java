package edu.neu.csye6220.models;

import edu.neu.csye6220.models.enums.IncomeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "income")
public class Income {
    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal amount;
    @Column(columnDefinition = "ENUM('salary', 'stock', 'investment', 'scholarship', 'other')")
    @Enumerated(EnumType.STRING)
    private IncomeType type;
    private Timestamp date;
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_id")
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

    public IncomeType getType() {
        return type;
    }

    public void setType(IncomeType type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
