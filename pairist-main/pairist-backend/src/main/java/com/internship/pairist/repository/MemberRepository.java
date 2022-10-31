package com.internship.pairist.repository;

import com.internship.pairist.model.Member;
import com.internship.pairist.model.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findByTeam(Team team);

}
