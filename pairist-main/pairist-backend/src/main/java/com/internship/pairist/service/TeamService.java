package com.internship.pairist.service;

import com.internship.pairist.model.Team;
import com.internship.pairist.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public TeamService(@Autowired TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public List findAll() {
        return (List)teamRepository.findAll();
    }

    public Team addTeam(String name, String pword) {
        Team team = new Team();
        team.setName(name);
        team.setPassword(pword);
        return teamRepository.save(team);
    }

    public Optional<Team> verifyPassword(String team_name, String pword) {
        Team actualTeam = teamRepository.findByName(team_name);
        Optional<Team> optTeam;
        if (pword.equals(actualTeam.getPassword())) {
            optTeam = Optional.ofNullable(actualTeam);
        }
        else {
            optTeam = Optional.ofNullable(null);
    }
        return optTeam;
}

    public Team getTeam(String teamName) {
        Team foundTeam = teamRepository.findByName(teamName);
        return foundTeam;
    }

}
