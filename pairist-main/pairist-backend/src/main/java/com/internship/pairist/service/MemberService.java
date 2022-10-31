package com.internship.pairist.service;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Team;
import com.internship.pairist.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    public MemberService(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public Optional<Member> addMember(String fName, String lName, long teamId) {
        Member newMember = new Member();
        Optional<Member> optMember;

        newMember.setlName(lName);
        newMember.setfName(fName);

        Team newTeam = new Team();
        newTeam.setId(teamId);
        newMember.setTeam(newTeam);

        try {
            optMember =  Optional.ofNullable(memberRepository.save(newMember));
        } catch (Exception e) {
            optMember = Optional.ofNullable(null);
        }

        return optMember;
    }

}
