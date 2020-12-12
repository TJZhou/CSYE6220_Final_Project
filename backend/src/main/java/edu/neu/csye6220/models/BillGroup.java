package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "bill_group")
public class BillGroup implements Serializable {
    @Id
    @Column(name = "group_id")
    private String id;

    @NotBlank
    @Size(max = 500)
    @Column(name = "group_name")
    private String groupName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_owner")
    private User groupOwner;

    @ManyToMany(mappedBy = "groupParticipated", fetch = FetchType.LAZY)
    private Collection<User> groupParticipants;

    @NotNull
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date createdAt;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billGroup", fetch = FetchType.LAZY)
    private Collection<Bill> bills;

    public BillGroup() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(User groupOwner) {
        this.groupOwner = groupOwner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<Bill> getBills() {
        return bills;
    }

    public void setBills(Collection<Bill> bills) {
        this.bills = bills;
    }

    public Collection<User> getGroupParticipants() {
        return groupParticipants;
    }

    public void setGroupParticipants(Collection<User> groupParticipants) {
        this.groupParticipants = groupParticipants;
    }
}
