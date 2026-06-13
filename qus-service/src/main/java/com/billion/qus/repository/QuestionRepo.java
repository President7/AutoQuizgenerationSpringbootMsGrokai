package com.billion.qus.repository;

import com.billion.qus.collections.Ques;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepo extends MongoRepository<Ques, String> {
    List<Ques> findByQuizId(Long quizId);

}

