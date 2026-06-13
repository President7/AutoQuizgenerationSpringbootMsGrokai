package com.billion.quiz.service;

import com.billion.quiz.dto.QuizDto;

import java.util.List;

public interface QuizService {

    public QuizDto createQuiz(QuizDto quizDto);

    QuizDto updateQuiz(String quizId, QuizDto quizDto);

    public void delteQuizById(String quizId);

    List<QuizDto> findAll();

    QuizDto finQuizId(String quizId);

    List<QuizDto> findByCategoryId(String categoryId);

//    public QuizDto updateQuizById(String quizId);






}
