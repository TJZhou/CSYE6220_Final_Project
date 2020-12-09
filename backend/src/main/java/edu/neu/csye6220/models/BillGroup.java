package edu.neu.csye6220.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye6220.utils.RegexUtil;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "bill_group")
public class BillGroup {
    @Id
    @Column(name = "group_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "group_name")
    private String groupName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_owner")
    private User groupOwner;

    @ManyToMany(mappedBy = "groupParticipated")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<User> groupParticipants;

    @NotNull
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date createdAt;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billGroup")
    @LazyCollection(LazyCollectionOption.FALSE)
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
