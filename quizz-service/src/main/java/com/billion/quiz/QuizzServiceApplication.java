package com.billion.quiz;

import com.billion.quiz.collections.Quiz;
import com.billion.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.UUID;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.billion.quiz")
@EnableDiscoveryClient
public class QuizzServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(QuizzServiceApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Quiz build = Quiz.builder()
//                .id(UUID.randomUUID().toString())
//                .name("Java")
//                .title("Java Thread")
//                .description("java basics OOPs")
//                .maxMarks(100)
//                .imageUrl("https://google.com")
//                .createdBy("billionaior ravi")
//                .passingMark(50)
//                .noOfQuestions(50)
//                .live(true)
//                .timeLimit(60).build();
//
//        Quiz save = quizRepository.save(build);
//        System.out.println("save with Id : " + save.getId());
   // }
}
