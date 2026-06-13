package com.billion.quiz.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class InformationService {

    @Bean
    public Supplier<String> getInformation() {
        return () -> "You are doing great work! Thank you!";
    }

    @Bean
    public Function<Information, String> consumerInformation() {
        //return s -> "THis is consumer test which will post method accepts ";
        return (information -> {
            System.out.println(information.name());
            System.out.println(information.phone());
            return "After successfull process: " + information.name() + " and " + information.phone();
        });
    }

    //RECEIVE ACKNOWLEDGE
    @Bean
    public Consumer<String> getQuizAcknowledged(){
        return information -> {
            System.out.println("quiz-acknowledged received! ");
            System.out.println(information);
        };
    }

    record Information(String name, String phone) {

    }


}
