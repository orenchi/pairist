package com.internship.pairist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(
        name="member",
        uniqueConstraints = {@UniqueConstraint(name="uniqueNameOnEachTeam", columnNames = {"fName", "lName",
                "team_id"})
        })
public class Member {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @JsonBackReference
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Member otherMember = (Member) other;
        if (this.fName != otherMember.getfName()) {
            return false;
        }

        else if (this.lName != otherMember.getlName()) {
            return false;
        }

        else if (this.id != otherMember.getId()) {
            return false;
        }

        else {
            return true;
        }

    }




}
