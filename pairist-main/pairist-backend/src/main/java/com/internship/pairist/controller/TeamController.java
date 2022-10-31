package com.internship.pairist.controller;

import com.internship.pairist.model.Team;
import com.internship.pairist.service.TeamService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamService teamService;

    public TeamController(@Autowired TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(path = "/addTeam")
    public ResponseEntity<Team> addTeam(@RequestParam String name, @RequestParam String pword) {
        if (Strings.isBlank(pword) || Strings.isBlank(name)) {
            return ResponseEntity.badRequest().build();
        }
        else {
            Team realTeam = teamService.addTeam(name, pword);
            return ResponseEntity.ok(realTeam);
        }
    }

    @GetMapping(path = "/getAllTeams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List allTeams = teamService.findAll();
        return ResponseEntity.ok(allTeams);
    }

    @PostMapping(path = "/verifyLogIn")
    public ResponseEntity<Team> verifyPassword(@RequestParam String teamName, @RequestParam String pword) {
        Optional<Team> optTeam = teamService.verifyPassword(teamName, pword);
        if (optTeam.isPresent()) {
            return ResponseEntity.ok(optTeam.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(path = "/getTeam")
    public ResponseEntity<Team> getTeam(@RequestParam String teamName) {
        return ResponseEntity.ok(teamService.getTeam(teamName));
    }

}
