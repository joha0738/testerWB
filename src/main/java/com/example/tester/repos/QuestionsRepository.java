package com.example.tester.repos;

import com.example.tester.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions,Long> {
    List<Questions> findBytestsId(Long id);
    Questions findByQuestion(String quest);
}