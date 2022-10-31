package com.internship.pairist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.pairist.model.Team;
import com.internship.pairist.service.TeamService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TeamControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TeamService teamService;

    @MockBean
    private MemberController memberController;

    @MockBean
    private PairController pairController;

    private Team team;
    private Team team2;

    private String teamName;
    private String team2Name;
    private String pword;
    private String team2pword;

    @BeforeAll
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        team = new Team();
        team2 = new Team();

        teamName = "team";
        team.setName(teamName);

        team2Name = "team2";
        team2.setName(team2Name);

        pword = "pword";
        team.setPassword(pword);

        team2pword = "pword2";
        team2.setPassword(team2pword);
    }
    @Test
    void getAllTeams_getsAllTeams() throws Exception {
        ArrayList<Team> teamList = new ArrayList<Team>();
        teamList.add(team);
        teamList.add(team2);

        when(teamService.findAll()).thenReturn(teamList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAllTeams").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2))).andDo(print());

        verify(teamService, times(1)).findAll();
    }

    @Test
    void getAllTeams_getsAllTeams_emptyTeamList() throws Exception {
        //Arrange
        ArrayList<Team> teamList = new ArrayList<Team>();
        when(teamService.findAll()).thenReturn(teamList);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllTeams").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(0))).andDo(print());
        verify(teamService, times(1)).findAll();
    }

    @Test
    void addTeam_addsOneTeam() throws Exception {


        when(teamService.addTeam(teamName,
                pword)).thenReturn(team);

        mockMvc.perform(MockMvcRequestBuilders.post("/addTeam?name="+teamName+"&pword="+pword)
                                              .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name")
                                              .value(teamName))
                                              .andExpect(jsonPath("$.password").value(pword));
        verify(teamService, times(1)).addTeam(teamName, pword);
    }

    @Test
    void addTeam_addsAnotherTeam() throws Exception {


        when(teamService.addTeam(team2Name,
                team2pword)).thenReturn(team2);

        mockMvc.perform(MockMvcRequestBuilders.post("/addTeam?name="+team2Name+"&pword="+team2pword)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name")
                        .value(team2Name))
                        .andExpect(jsonPath("$.password").value(team2pword));

        verify(teamService, times(1)).addTeam(team2Name, team2pword);
    }

    @Test
    void addTeam_addsWithEmptyPassword() throws Exception {
        String team3Name = "team3";
        String pword = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/addTeam?name="+team3Name+"&pword="+pword).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    void addTeam_addsWithEmptyTeamName() throws Exception {
        String team4Name = "";
        String pword = "password";
        mockMvc.perform(MockMvcRequestBuilders.post("/addTeam?name="+team4Name+"&pword="+pword).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void verifyPW_correct() throws Exception {
        when(teamService.verifyPassword(teamName, pword)).thenReturn(Optional.ofNullable(team));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/verifyLogIn?teamName=" + teamName + "&pword=" + pword).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        Team actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Team>() {});
        verify(teamService, times(1)).verifyPassword(teamName, pword);
        assertEquals(team.getName(), actual.getName());
        assertEquals(team.getPassword(), actual.getPassword());
    }

    @Test
    void verifyPW_correct2() throws Exception {
        when(teamService.verifyPassword(team2Name, team2pword)).thenReturn(Optional.ofNullable(team2));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/verifyLogIn?teamName=" + team2Name + "&pword=" + team2pword).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        Team actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Team>() {});
        verify(teamService, times(1)).verifyPassword(team2Name, team2pword);
        assertEquals(team2.getName(), actual.getName());
        assertEquals(team2.getPassword(), actual.getPassword());
    }

    @Test
    void verifyPW_incorrect() throws Exception {
        when(teamService.verifyPassword(teamName, pword)).thenReturn(Optional.ofNullable(null));
        mockMvc.perform(MockMvcRequestBuilders.post("/verifyLogIn?teamName="+teamName+"&pword="+team2pword).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
        verify(teamService, times(1)).verifyPassword(teamName, team2pword);
    }


}
