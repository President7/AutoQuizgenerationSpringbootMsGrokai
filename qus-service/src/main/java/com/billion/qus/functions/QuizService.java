package com.billion.qus.functions;

import com.billion.qus.service.QuestionGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class QuizService {
private static final Logger LOGGER= LoggerFactory.getLogger(QuizService.class);
@Autowired
private QuestionGenerator questionGenerator;

    @Bean(name = "getQuizBinding")
    public Function<QuizDto, String> getQuizBinding() {
        return quizDto -> {
            LOGGER.error("Quiz creatd event received successfully...()");
            System.out.println("Quiz creatd event received successfully...() ");
            System.out.println("Quiz creatd event received successfully...() " + quizDto.getTitle());
            System.out.println("Quiz creatd event received successfully...() " + quizDto.getId());
            System.out.println("Quiz creatd event received successfully...() " + quizDto.getCategoryId());
          this.questionGenerator.generateAndSaveQuestions(quizDto);
            return "The quiz generated successfully..";
        };
    }
}
