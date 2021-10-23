package com.example.tester.repos;

import com.example.tester.models.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestsRepository extends JpaRepository<Tests,Long> {
    Tests findByTitle (String title);
}
