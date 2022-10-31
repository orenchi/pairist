package com.internship.pairist.controller;


import com.internship.pairist.model.Member;
import com.internship.pairist.model.Pair;
import com.internship.pairist.model.Team;
import com.internship.pairist.repository.MemberRepository;
import com.internship.pairist.service.PairService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PairControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @MockBean
    private PairService pairService;

    @MockBean
    private MemberController memberController;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private TeamController teamController;

    @BeforeAll
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addPair_addsOnePair() throws Exception {
        
        long member1Id = 1;
        long member2Id = 2;
        long teamId = 1;


        Pair pair = new Pair();

        Member member1 = new Member();
        member1.setId(member1Id);

        Member member2= new Member();
        member2.setId(member2Id);


        ArrayList<Member> memberList = new ArrayList<Member>();
        memberList.add(member1);
        memberList.add(member2);
        pair.setMembers(memberList);

        Team team = new Team();
        team.setId(teamId);
        pair.setTeam(team);

        when(pairService.addPair(member1Id, member2Id, teamId)).thenReturn(pair);


        mockMvc.perform(MockMvcRequestBuilders.post("/addPair?member1Id=" + member1Id +"&member2Id=" + member2Id + "&teamId=" + teamId).contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.members[0].id").value(member1Id)).andExpect(jsonPath("$.members[1].id").value(member2Id));
        verify(pairService, times(1)).addPair(member1Id,member2Id,teamId);
    }
    @Test
    void addPair_addsAnotherPair() throws Exception {

        long member1Id = 3;
        long member2Id = 4;
        long teamId = 2;

        Pair pair = new Pair();

        Member member1 = new Member();
        member1.setId(member1Id);

        Member member2= new Member();
        member2.setId(member2Id);

        ArrayList<Member> memberList = new ArrayList<Member>();
        memberList.add(member1);
        memberList.add(member2);
        pair.setMembers(memberList);

        Team team = new Team();
        team.setId(teamId);
        pair.setTeam(team);

        when(pairService.addPair(member1Id, member2Id, teamId)).thenReturn(pair);


        mockMvc.perform(MockMvcRequestBuilders.post("/addPair?member1Id=" + member1Id +"&member2Id=" + member2Id + "&teamId=" + teamId).contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.members[0].id").value(member1Id)).andExpect(jsonPath("$.members[1].id").value(member2Id));
        verify(pairService, times(1)).addPair(member1Id,member2Id,teamId);
    }




}
