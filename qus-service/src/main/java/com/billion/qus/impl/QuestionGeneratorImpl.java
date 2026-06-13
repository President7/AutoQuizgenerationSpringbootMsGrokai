package com.billion.qus.impl;

import com.billion.qus.collections.Ques;
import com.billion.qus.dto.Question;
import com.billion.qus.functions.QuizDto;
import com.billion.qus.repository.QuestionRepo;
import com.billion.qus.service.QuestionGenerator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionGeneratorImpl implements QuestionGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionGeneratorImpl.class);

    private final ChatClient chatClient;
    private final QuestionRepo questionRepo;
    private final ModelMapper modelMapper;

    public QuestionGeneratorImpl(ChatClient.Builder builder, QuestionRepo questionRepo, ModelMapper modelMapper) {
        this.chatClient = builder.build();
        this.questionRepo = questionRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void generateAndSaveQuestions(QuizDto quizDto) {
        if (quizDto == null) {
            LOGGER.warn("QuizDto is null, nothing to generate.");
            return;
        }

        List<Question> questions = this.generateQuistion(quizDto.getTitle(), 10, quizDto.getDescription());
        if (questions == null || questions.isEmpty()) {
            LOGGER.warn("No questions returned from generator for quiz id={} name={}", quizDto.getId(), quizDto.getTitle());
            return;
        }

        // Correct mapping: map each Question -> Ques (not the whole list!)
        List<Ques> listOfQuestions = questions.stream()
                .map(question -> {
                    // set quiz id on question DTO (if needed)
                    question.setQuizId(quizDto.getId());
                    // Map single question to Ques
                    Ques mapped = this.modelMapper.map(question, Ques.class);
                    // ensure quizId propagated to mapped entity (double-check field name)
                    mapped.setQuizId(quizDto.getId());
                    return mapped;
                })
                .collect(Collectors.toList());

        LOGGER.info("About to save {} questions for quiz id={}", listOfQuestions.size(), quizDto.getId());
         LOGGER.debug("About to save {} questions for quiz id={}", listOfQuestions.size(), quizDto.getId());
        try {
            questionRepo.saveAll(listOfQuestions);
            LOGGER.info("Successfully saved {} questions for quiz id={}", listOfQuestions.size(), quizDto.getId());
        } catch (Exception ex) {
            LOGGER.error("Error saving questions for quiz id=" + quizDto.getId(), ex);
        }

        // optional: log the saved questions
        listOfQuestions.forEach(q -> LOGGER.error("Saved question: {}", q.getQuestion()));
        listOfQuestions.forEach(q -> LOGGER.error("Saved questionId: {}", q.getQuestionsId()));
        listOfQuestions.forEach(q -> LOGGER.error("Saved option1: {}", q.getOption1()));
        listOfQuestions.forEach(q -> LOGGER.error("Saved option2: {}", q.getOption2()));
        listOfQuestions.forEach(q -> LOGGER.error("Saved Answer: {}", q.getAnswer()));
    }

    @Override
    public List<Question> getAllQuizsByQuizId(QuizDto quizDto) {
        if (quizDto == null || quizDto.getId()=="") {
            LOGGER.info("Quiz Dto is null nothing will get");
            return Collections.emptyList();
        }
        List<Ques> allQues = questionRepo.findByQuizId(Long.valueOf(quizDto.getId()));
      return  allQues.stream().map(ques -> {
           // ques.setQuizId(quizDto.getId());
            return modelMapper.map(ques, Question.class);
        }).collect(Collectors.toList());

    }

    @Override
    public List<Question> generateQuistion(String quizName, int numberOfQuestions, String description) {
        LOGGER.info("generateQuistion() - quizName='{}' numberOfQuestions={}", quizName, numberOfQuestions);

        // Simpler, explicit prompt formatting — avoids depending on templating behavior.
        String systemPrompt = "As a Coding, Technology, Programming framework expert, generate high-quality quiz questions.";
        String userPrompt = String.format("Generate %d questions for the quiz titled \"%s\".\nDescription: %s",
                numberOfQuestions, quizName, description == null ? "" : description);

        try {
            List<Question> result = this.chatClient.prompt()
                    .system(systemPrompt)
                    .user(prompt -> prompt.text(userPrompt))
                    .call()
                    .entity(new ParameterizedTypeReference<List<Question>>() {
                    });

            if (result == null) {
                LOGGER.warn("ChatClient returned null for questions (quizName={})", quizName);
                return Collections.emptyList();
            }
            return result;
        } catch (Exception ex) {
            LOGGER.error("Error calling chat client for quizName=" + quizName, ex);
            return Collections.emptyList();
        }
    }
}
