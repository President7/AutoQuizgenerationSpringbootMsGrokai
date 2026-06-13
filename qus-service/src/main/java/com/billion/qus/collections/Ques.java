package com.billion.qus.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ques {
        @Id
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
