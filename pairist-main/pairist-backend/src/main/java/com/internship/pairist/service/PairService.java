package com.internship.pairist.service;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Pair;
import com.internship.pairist.model.Task;
import com.internship.pairist.model.Team;

import com.internship.pairist.repository.MemberRepository;
import com.internship.pairist.repository.PairRepository;
import com.internship.pairist.repository.TaskRepository;
import com.internship.pairist.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class PairService {
    @Autowired
    private PairRepository pairRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamRepository teamRepository;

    public PairService(@Autowired PairRepository pairRepository, @Autowired MemberRepository memberRepository, @Autowired TaskRepository taskRepository) {
        this.pairRepository = pairRepository;
        this.memberRepository= memberRepository;
    }



    public Pair addPair(long member1Id, long member2Id, long teamId) {
        //find members ?
        Optional<Member> member1Optional = memberRepository.findById(member1Id);
        Optional<Member> member2Optional = memberRepository.findById(member2Id);

        Member member1 = new Member();
        member1.setId(member1Id);

        Member member2 = new Member();
        member2.setId(member2Id);

        if (member1Optional.isPresent()){
            member1 = member1Optional.get();
        }
        if (member2Optional.isPresent()){
            member2 = member2Optional.get();
        }
        Pair newPair = new Pair();


        List<Member> memberList = new ArrayList<Member>();
        memberList.add(member1);
        memberList.add(member2);

        newPair.setMembers(memberList);
        Team tempTeam = new Team();
        tempTeam.setId(teamId);
        newPair.setTeam(tempTeam);

        return pairRepository.save(newPair);

    }

    public ArrayList<Pair> getPairs(long team_id) {
        ArrayList<Pair> pairList = new ArrayList<Pair>();

        //pairRepository.fin

        return pairList;
    }

    public Pair assignTask(long pairId, Task task) {
        Optional<Pair> byId = pairRepository.findById(pairId);
        Pair pair = byId.get();

        Task savedTask = taskRepository.save(task);
        pair.setCurrentTask(savedTask);

        return pairRepository.save(pair);
    }


    public List<Pair> randomPair(long teamId) {

        Random rand = new Random();
        Team currTeam = teamRepository.findById(teamId).get();
        pairRepository.deleteAllByTeamId(currTeam.getId());
        List<Member> memberList = memberRepository.findByTeam(currTeam);

        List<Pair> pairList = new ArrayList<Pair>();

        while (memberList.size() >= 2) {
                int rand1 = rand.nextInt(memberList.size());
                int rand2 = rand.nextInt(memberList.size());

                while (rand1 == rand2) {
                    rand2 = rand.nextInt(memberList.size());
                }

                Member randMember1 = memberList.get(rand1);
                Member randMember2 = memberList.get(rand2);
                List<Member> currMemberList = new ArrayList<>();
                currMemberList.add(randMember1);
                currMemberList.add(randMember2);

                Pair newPair = new Pair();
                newPair.setMembers(currMemberList);
                newPair.setTeam(currTeam);

                pairList.add(pairRepository.save(newPair));

                memberList.remove(randMember1);
                memberList.remove(randMember2);

        }

//        currTeam.setPairs(pairList);
//        teamRepository.save(currTeam);
        return pairList;
    }
}
