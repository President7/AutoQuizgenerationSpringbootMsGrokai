package com.billion.quiz.repository;

import com.billion.quiz.collections.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {
    List<Quiz> findByTitle(String title);
    List<Quiz>findByCategoryId(String categoryId);
}

