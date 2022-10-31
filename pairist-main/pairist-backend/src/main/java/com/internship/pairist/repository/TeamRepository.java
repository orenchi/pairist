package com.internship.pairist.repository;

import com.internship.pairist.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository  extends CrudRepository<Team, Long> {

    Team findByName(String team_name);

}
