package com.billion.quiz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/config")
@RefreshScope
public class ConfitController {
private static final Logger LOGGER= LoggerFactory.getLogger(ConfitController.class);
    @Value("${config.value}")
    private String configValue;

    @GetMapping
    public String checkConfig(){
        LOGGER.error(" configValue : " +configValue);
        return configValue;
    }
}
