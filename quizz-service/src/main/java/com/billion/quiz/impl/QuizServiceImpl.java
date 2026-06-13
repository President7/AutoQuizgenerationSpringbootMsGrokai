package com.billion.quiz.impl;

import com.billion.quiz.collections.Quiz;
import com.billion.quiz.config.ResourceNotFoundException;
import com.billion.quiz.dto.CategoryDto;
import com.billion.quiz.dto.QuizDto;
import com.billion.quiz.repository.QuizRepository;
import com.billion.quiz.service.CategoryFeignService;
import com.billion.quiz.service.CategoryService;
import com.billion.quiz.service.QuizService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class QuizServiceImpl implements QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private QuizRepository quizRepository;
    private ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private CategoryService categoryService;
    private CategoryFeignService categoryFeignService;
    private StreamBridge streamBridge;


    public QuizServiceImpl(CategoryFeignService categoryFeignService, QuizRepository quizRepository, ModelMapper modelMapper,
                           RestTemplate restTemplate, @Qualifier("categoryServiceImpl") CategoryService categoryService, StreamBridge streamBridge) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.categoryService = categoryService;
        this.categoryFeignService = categoryFeignService;
        this.streamBridge = streamBridge;
    }

    @Override
    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz createQuiz = modelMapper.map(quizDto, Quiz.class);
        createQuiz.setId(UUID.randomUUID().toString());

        String url = "lb://QUIZZ-CATEGORY/quiz/category/" + quizDto.getCategoryId();
        CategoryDto categoryDto = restTemplate.getForObject(url, CategoryDto.class);
        logger.error("title : " + categoryDto.getTitle());
        Quiz save = quizRepository.save(createQuiz);
        QuizDto quizDto1 = modelMapper.map(save, QuizDto.class);
        quizDto1.setCategoryDto(categoryDto);

        publishQuizDtoEvenet(quizDto1);

        return quizDto1;
    }


    private void publishQuizDtoEvenet(QuizDto quizDto) {
        logger.error("Quiz is created success going to publish quiz created event");
        var success = this.streamBridge.send("quizCreatedBinding-out-0", quizDto);
        if (success)
            logger.error("Quiz event sent to brocker successFully");
        else
            logger.error("Quiz event not sent to brocker");
    }


    @Override
    public QuizDto finQuizId(String quizId) {
        logger.error(" inside findById url ....");
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("The quizID not found : " + quizId));
        logger.info("categoryId " + quiz.getCategoryId());
        logger.info("categoryId " + quiz.toString());
        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
        String categoryId = quiz.getCategoryId();
        logger.info("categoryId " + categoryId);
        String url = "lb://QUIZZ-CATEGORY/quiz/category/" + categoryId;
        logger.info("url " + url);
        CategoryDto categoryDto = restTemplate.getForObject(url, CategoryDto.class);
        quizDto.setCategoryDto(categoryDto);
        return quizDto;
    }

    @Override
    public void delteQuizById(String quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("The quizID not found : " + quizId));
        quizRepository.delete(quiz);
        logger.error("delete done by Id : " + quizId);
    }

    @Override
    public QuizDto updateQuiz(String quizId, QuizDto quizDto) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("The quizId not found : " + quizId));
        quiz.setId(quizId);
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setName(quizDto.getName());
        quiz.setMaxMarks(quizDto.getMaxMarks());
        quiz.setPassingMark(quizDto.getPassingMark());
        quiz.setTimeLimit(quizDto.getTimeLimit());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setCreatedBy(quizDto.getCreatedBy());
        quiz.setTimeLimit(quizDto.getTimeLimit());
        quiz.setNoOfQuestions(quizDto.getNoOfQuestions());
        quiz.setCategoryId(quizDto.getCategoryId());
        quiz.setLive(quizDto.isLive());

        Quiz save = quizRepository.save(quiz);
        QuizDto mapQuizDto = modelMapper.map(save, QuizDto.class);
        logger.error("update done by Id : " + quizId);
        return mapQuizDto;
    }

    @Override
    public List<QuizDto> findAll() {
        List<Quiz> quizList = quizRepository.findAll();
        logger.error("quizList : " + quizList.toString());
        List<QuizDto> quizDtos = quizList.stream().map(quiz -> {
            String categoryId = quiz.getCategoryId();
            logger.error("categoryId : " + categoryId);
            QuizDto mapQuizDto = modelMapper.map(quiz, QuizDto.class);
            CategoryDto categorybyId = categoryService.findById(categoryId);
            mapQuizDto.setCategoryDto(categorybyId);
            return mapQuizDto;

        }).toList();
        logger.error("quizList before return : " + quizList);
        return quizDtos;
    }

    @Override
    public List<QuizDto> findByCategoryId(String categoryId) {
        List<Quiz> quizList = quizRepository.findByCategoryId(categoryId);
        List<QuizDto> list = quizList.stream().map(quiz ->
        {
            QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
            CategoryDto categoryDto = null;
            try {
                categoryDto = categoryFeignService.findById(quizDto.getCategoryId());

            } catch (FeignException.NotFound e) {
                logger.error("Recource not found exception", e);
            }
            quizDto.setCategoryDto(categoryDto);
            //  modelMapper.map(categoryDto, QuizDto.class);
            return quizDto;
        }).toList();
        return list;
    }
}
