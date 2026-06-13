package com.billion.quiz.controller;

import com.billion.quiz.dto.QuizDto;
import com.billion.quiz.service.QuizService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz/v1/quizs")
public class QuizController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizController.class);
    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuizs(@RequestBody QuizDto quizDto) {
        QuizDto quiz = quizService.createQuiz(quizDto);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    int i = 0;

    @GetMapping
    @CircuitBreaker(name = "quizCB", fallbackMethod = "quizFallback")
    public ResponseEntity<List<QuizDto>> getQuizs() {

        LOGGER.error("inside looger: Fetching all quezzs....." + i);

        /*
        i++;
        List<QuizDto> allQuizs = quizService.findAll();
        if(i< 3)
            throw new RuntimeException("Quizz Service Down PLease try again latter : RE ");
        else
            return new ResponseEntity<>(allQuizs, HttpStatus.OK);
    */


        List<QuizDto> allQuizs = quizService.findAll();
        return new ResponseEntity<>(allQuizs, HttpStatus.OK);
    }

    public ResponseEntity<List<QuizDto>> quizFallback(Throwable throwable) {

        LOGGER.error("inside looger: Fetching all quezzs....." + i);
        //List<QuizDto> allQuizs = quizService.findAll();
        return new ResponseEntity<>(List.of(),HttpStatus.SERVICE_UNAVAILABLE);
    }
/*

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDto> updateQuizById(@PathVariable String quizId) {
        QuizDto quizDto = quizService.updateQuizById(quizId);
        return new ResponseEntity<>(quizDto, HttpStatus.OK);
    }
*/

    @GetMapping("/{quizId}")

    public ResponseEntity<QuizDto> quizsById(@PathVariable String quizId) {
        QuizDto quizDto = quizService.finQuizId(quizId);
        return new ResponseEntity<>(quizDto, HttpStatus.OK);
    }


    @DeleteMapping("/{quizId}")
    public ResponseEntity<QuizDto> deleteQuizsById(@PathVariable String quizId) {
        quizService.delteQuizById(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDto> updateQuizs(@PathVariable String quizId, @RequestBody QuizDto quizDto) {
        QuizDto quizDtoUpdate = quizService.updateQuiz(quizId, quizDto);
        return new ResponseEntity<>(quizDtoUpdate, HttpStatus.OK);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<QuizDto>> findByCategory(@PathVariable String categoryId) {
        List<QuizDto> byCategoryId = quizService.findByCategoryId(categoryId);
        return new ResponseEntity<>(byCategoryId, HttpStatus.OK);
    }


}
