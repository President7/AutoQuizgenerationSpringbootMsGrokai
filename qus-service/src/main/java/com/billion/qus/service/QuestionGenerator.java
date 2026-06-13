package com.billion.qus.service;

import com.billion.qus.dto.Question;
import com.billion.qus.functions.QuizDto;

import java.util.List;

public interface QuestionGenerator {
List<Question > generateQuistion(String quizName, int numberOfQuestions, String description);
void generateAndSaveQuestions(QuizDto quizDto);

List<Question> getAllQuizsByQuizId(QuizDto quizDto);
}
