package com.internship.pairist.controller;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Team;
import com.internship.pairist.service.MemberService;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class MemberControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MemberController memberController;

    @MockBean
    private MemberService memberService;

    @BeforeAll
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addMember() throws Exception {
        Member member = new Member();
        long teamId = 5;
        String fName = "bob";
        String lName = "W";

        Team team = new Team();

        member.setfName(fName);
        member.setlName(lName);
        member.setTeam(team);
        member.getTeam().setId(teamId);

        when(memberService.addMember(fName, lName, teamId)).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName="+fName+"&lName="+lName+"&teamId="+teamId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.fName").value(fName)).andExpect(jsonPath("$.lName").value(lName));
        verify(memberService, times(1)).addMember(fName, lName, teamId);


    }

    @Test
    void addAnotherMember() throws Exception {
        Member member = new Member();
        long teamId = 6;
        String fName = "alice";
        String lName = "Z";

        Team team = new Team();
        team.setId(teamId);

        member.setfName(fName);
        member.setlName(lName);
        member.setTeam(team);

        when(memberService.addMember(fName, lName, teamId)).thenReturn(Optional.ofNullable(member));

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName="+fName+"&lName="+lName+"&teamId="+teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fName").value(fName)).andExpect(jsonPath("$.lName").value(lName));
        verify(memberService, times(1)).addMember(fName, lName, teamId);
    }

    @Test
    void addDuplicateMember() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();

        long teamId = 6;
        String fName = "alice";
        String lName = "Z";

        Team team = new Team();
        team.setId(teamId);

        member1.setfName(fName);
        member1.setlName(lName);
        member1.setTeam(team);

        member2.setfName(fName);
        member2.setlName(lName);
        member2.setTeam(team);

        when(memberService.addMember(fName, lName, teamId)).thenReturn(Optional.ofNullable(member1));

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName="+fName+"&lName="+lName+"&teamId="+teamId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.fName").value(fName))
                .andExpect(jsonPath("$.lName").value(lName))
                .andExpect(status().isOk());

        when(memberService.addMember(fName, lName, teamId)).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName="+fName+"&lName="+lName+"&teamId="+teamId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

        verify(memberService, times(2)).addMember(fName, lName, teamId);
    }

    @Test
    void addMember_addsMemberMissingField() throws Exception{
        Member mem1 = new Member();
        String notEmpty = "W";
        String empty = "";
        long teamId = 6;
        mem1.setId(teamId);
        mem1.setfName(empty);
        mem1.setlName(notEmpty);

        when(memberService.addMember(empty, notEmpty, teamId)).thenReturn(Optional.empty());
        when(memberService.addMember(notEmpty, empty, teamId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName=" + empty + "&lName=" + notEmpty + "&teamId=" + teamId))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/addMember?fName=" + notEmpty + "&lName=" + empty + "&teamId" +
                        "=" + teamId))
                .andExpect(status().isBadRequest());

        verify(memberService, times(0)).addMember(empty, notEmpty, teamId);
        verify(memberService, times(0)).addMember(notEmpty, empty, teamId);
    }
}
