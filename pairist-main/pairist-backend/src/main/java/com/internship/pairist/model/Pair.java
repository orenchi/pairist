package com.internship.pairist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Pair{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<Member> members; //maybe should just use a list here

    @ManyToOne
    @JoinColumn(name= "team_id")
    private Team team;



    @OneToOne
    private Task currentTask;

    @JsonBackReference
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Pair otherPair = (Pair) other;
        if (this.members.size() != otherPair.getMembers().size()) {
            return false;
        }
        for (int i = 0; i < this.members.size(); i++) {
            Member this_member = this.members.get(i);
            Member other_member = otherPair.getMembers().get(i);
            if (!this_member.equals(other_member)) {
                return false;
            }
        }return true;
    }

}
