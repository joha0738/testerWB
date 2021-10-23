package com.example.tester.repos;

import com.example.tester.models.Results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Results,Long> {
    List<Results> findByUserAndTest (String user,String test);
    List <Results> findByTest (String test);
    Results findByUserAndQuest (String user, String quest);
}
