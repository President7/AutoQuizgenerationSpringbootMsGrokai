package com.billion.qus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String questionsId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String quizId;
}
