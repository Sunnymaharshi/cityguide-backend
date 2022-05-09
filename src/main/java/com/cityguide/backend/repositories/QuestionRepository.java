package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
