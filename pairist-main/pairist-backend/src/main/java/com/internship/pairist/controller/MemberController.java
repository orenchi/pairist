package com.internship.pairist.controller;

import com.internship.pairist.model.Member;
import com.internship.pairist.service.MemberService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    public MemberController(@Autowired MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(path = "/addMember")
    public ResponseEntity<Member> addMember(@RequestParam String fName, @RequestParam String lName,
                                            @RequestParam Long teamId) {
        Member newMemb;

        if (Strings.isBlank(fName) || Strings.isBlank(lName)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Member> newMember = memberService.addMember(fName, lName, teamId);

        if (newMember.isPresent()) {
            return ResponseEntity.ok(newMember.get());
        } else {
            return ResponseEntity.badRequest().build();
        }


    }



}
