package com.internship.pairist.repository;

import com.internship.pairist.model.Pair;
import com.internship.pairist.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import javax.transaction.Transactional;

public interface PairRepository extends CrudRepository<Pair, Long> {

    @Transactional
    void deleteAllByTeamId(long team_id);
    }



