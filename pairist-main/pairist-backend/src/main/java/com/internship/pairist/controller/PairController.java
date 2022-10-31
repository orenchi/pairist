package com.internship.pairist.controller;

import com.internship.pairist.model.Pair;
import com.internship.pairist.model.Task;
import com.internship.pairist.service.PairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class PairController {

    @Autowired
    private PairService pairService;

    public PairController(@Autowired PairService pairService) { this.pairService = pairService; }


    @PostMapping(path = "/addPair")
    public ResponseEntity<Pair> addPair(@RequestParam long member1Id, @RequestParam long member2Id, @RequestParam long teamId) {
        Pair pair = pairService.addPair(member1Id, member2Id, teamId);
        return ResponseEntity.ok(pair);
    }

    @PutMapping(path = "/assignTask")
    public ResponseEntity assignTask(@RequestParam long pairId, @RequestBody Task task){
        return ResponseEntity.ok(pairService.assignTask(pairId, task));
    }

    @PutMapping(path = "/randomPair")
    public ResponseEntity<List<Pair>> randomPair(@RequestParam long teamId) {
        List<Pair> pair = pairService.randomPair(teamId);
        return ResponseEntity.ok(pair);
    }

}
