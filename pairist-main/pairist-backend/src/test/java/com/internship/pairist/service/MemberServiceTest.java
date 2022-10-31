package com.internship.pairist.service;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Team;
import com.internship.pairist.repository.MemberRepository;
import com.internship.pairist.service.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class MemberServiceTest {

    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @BeforeAll
    public void setup() {
        this.memberService = new MemberService(memberRepository);
    }


    @Test
    public void addMember_addsOneMember() {
        Member member = new Member();
        member.setfName("bob");
        member.setlName("w");
        member.setTeam(new Team());
        member.getTeam().setId(5);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Optional<Member> actualMember = memberService.addMember(member.getfName(),
                member.getlName(),
                member.getTeam().getId());
        ArgumentCaptor<Member> memCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberRepository).save(memCaptor.capture());
        Member captured = memCaptor.getValue();
        assertEquals(captured.getfName(), member.getfName());
        assertEquals(captured.getlName(), member.getlName());
        assertEquals(captured.getTeam().getId(), member.getTeam().getId());

        assertSame(member, actualMember.get());
    }

    @Test
    public void addMember_addsAnotherMember() {
        Member member = new Member();
        member.setfName("joyce");
        member.setlName("wu");
        member.setTeam(new Team());
        member.getTeam().setId(6);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Optional<Member> actualMember = memberService.addMember(member.getfName(),
                member.getlName(),
                member.getTeam().getId());
        ArgumentCaptor<Member> memCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberRepository).save(memCaptor.capture());
        Member captured = memCaptor.getValue();
        assertEquals(captured.getfName(), member.getfName());
        assertEquals(captured.getlName(), member.getlName());
        assertEquals(captured.getTeam().getId(), member.getTeam().getId());

        assertSame(member, actualMember.get());
    }

    @Test
    public void addMember_addsDuplicate() {
        Member member = new Member();
        member.setfName("joyce");
        member.setlName("wu");
        member.setTeam(new Team());
        member.getTeam().setId(6);

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Optional<Member> actualMember = memberService.addMember(member.getfName(),
                member.getlName(),
                member.getTeam().getId());
        ArgumentCaptor<Member> memCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberRepository).save(memCaptor.capture());
        Member captured = memCaptor.getValue();
        assertEquals(captured.getfName(), member.getfName());
        assertEquals(captured.getlName(), member.getlName());
        assertEquals(captured.getTeam().getId(), member.getTeam().getId());

        when(memberRepository.save(any(Member.class))).thenThrow(new RuntimeException());

        actualMember = memberService.addMember(member.getfName(),
                member.getlName(),
                member.getTeam().getId());


        assert(actualMember.isEmpty());
    }
}
