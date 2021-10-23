package com.example.tester.repos;

import com.example.tester.models.Answers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswersRepository extends JpaRepository<Answers,Long> {
    List<Answers> findByQuestionsId (Long id);
}
