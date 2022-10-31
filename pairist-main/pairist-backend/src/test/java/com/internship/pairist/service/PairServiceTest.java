package com.internship.pairist.service;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Pair;
import com.internship.pairist.model.Team;
import com.internship.pairist.repository.MemberRepository;
import com.internship.pairist.repository.PairRepository;
import com.internship.pairist.repository.TaskRepository;
import com.internship.pairist.service.PairService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PairServiceTest {
    private PairService pairService;

    @MockBean
    private PairRepository pairRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private TaskRepository taskRepository;

    @BeforeAll
    public void setup() {this.pairService = new PairService(pairRepository, memberRepository, taskRepository);}

    @Test
    void addPair_addsOnePair() {
        long memberID1 = 1;
        long memberID2 = 2;
        long teamId = 1;
        //create pair object to pass to repo
        Pair pair = new Pair();

        Member mem1 = new Member();
        mem1.setId(memberID1);
        mem1.setfName("cam");
        mem1.setlName("t");

        Member mem2 = new Member();
        mem2.setId(memberID2);
        mem2.setfName("joyce");
        mem2.setlName("w");

        Team team = new Team();
        team.setId(teamId);

        ArrayList<Member> members = new ArrayList<Member>(Arrays.asList(mem1, mem2));
        pair.setMembers(members);
        pair.setTeam(team);

        when(pairRepository.save(any(Pair.class))).thenReturn(pair);
        when(memberRepository.findById(memberID1)).thenReturn(Optional.of(mem1));
        when(memberRepository.findById(memberID2)).thenReturn(Optional.of(mem2));

        Pair actualPair = pairService.addPair(memberID1, memberID2, teamId);
        ArgumentCaptor<Pair> pairCaptor = ArgumentCaptor.forClass(Pair.class);

        verify(pairRepository).save(pairCaptor.capture());
        verify(memberRepository, times(1)).findById(memberID1);
        verify(memberRepository, times(1)).findById(memberID2);

        Pair capturedArgument = pairCaptor.getValue();
        assertEquals(pair.getMembers(), capturedArgument.getMembers());
        assertEquals(pair.getTeam(), capturedArgument.getTeam());
        assertSame(pair, actualPair);

        assertEquals(mem1.getfName(), capturedArgument.getMembers().get(0).getfName());
        assertEquals(mem1.getlName(), capturedArgument.getMembers().get(0).getlName());

        assertEquals(mem2.getfName(), capturedArgument.getMembers().get(1).getfName());
        assertEquals(mem2.getlName(), capturedArgument.getMembers().get(1).getlName());

        assertEquals(mem1.getId(), memberID1);
        assertEquals(mem2.getId(), memberID2);

    }
    @Test
    void addPair_addsAnotherPair() {
        long memberID1 = 3;
        long memberID2 = 4;
        long teamId = 2;
        //create pair object to pass to repo
        Pair pair = new Pair();

        Member mem1 = new Member();
        mem1.setId(memberID1);
        mem1.setfName("glen");
        mem1.setlName("t");

        Member mem2 = new Member();
        mem2.setId(memberID2);
        mem2.setfName("haku");
        mem2.setlName("w");

        Team team = new Team();
        team.setId(teamId);

        ArrayList<Member> members = new ArrayList<Member>(Arrays.asList(mem1, mem2));
        pair.setMembers(members);
        pair.setTeam(team);

        when(pairRepository.save(any(Pair.class))).thenReturn(pair);
        when(memberRepository.findById(memberID1)).thenReturn(Optional.of(mem1));
        when(memberRepository.findById(memberID2)).thenReturn(Optional.of(mem2));

        Pair actualPair = pairService.addPair(memberID1, memberID2, teamId);
        ArgumentCaptor<Pair> pairCaptor = ArgumentCaptor.forClass(Pair.class);

        verify(memberRepository, times(1)).findById(memberID1);
        verify(memberRepository, times(1)).findById(memberID2);

        verify(pairRepository).save(pairCaptor.capture());
        Pair capturedArgument = pairCaptor.getValue();
        assertEquals(pair.getMembers(), capturedArgument.getMembers());
        assertEquals(pair.getTeam(), capturedArgument.getTeam());
        assertSame(pair, actualPair);

        assertEquals(mem1.getfName(), capturedArgument.getMembers().get(0).getfName());
        assertEquals(mem1.getlName(), capturedArgument.getMembers().get(0).getlName());

        assertEquals(mem2.getfName(), capturedArgument.getMembers().get(1).getfName());
        assertEquals(mem2.getlName(), capturedArgument.getMembers().get(1).getlName());

        assertEquals(mem1.getId(), memberID1);
        assertEquals(mem2.getId(), memberID2);
    }

//    @Test
//    void addRandomPair_addsOneRando() {
//        long memberID1 = 2;
//        long memberID2 = 1;
//        long memberID3 = 4;
//
//        Member mem1 = new Member();
//        mem1.setId(memberID1);
//        mem1.setfName("juice");
//        mem1.setlName("wu");
//
//        Member mem2 = new Member();
//        mem2.setId(memberID2);
//        mem2.setfName("miyuki");
//        mem2.setlName("kazuya");
//
//        Member mem3 = new Member();
//        mem3.setId(memberID3);
//        mem3.setfName("furuya");
//        mem3.setlName("satoru");
//
//        List<Member> memberList = new ArrayList<>();
//        memberList.add(mem1);
//        memberList.add(mem2);
//        memberList.add(mem3);
//
//        when(pairRepository.save(any(Pair.class))).thenReturn(pair);
//
//    }

}

 


