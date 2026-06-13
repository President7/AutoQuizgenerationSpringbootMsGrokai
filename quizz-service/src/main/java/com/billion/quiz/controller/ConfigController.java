package com.billion.quiz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/config")
@RefreshScope
public class ConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @Value("${config.value}")
    String configValue;

    @GetMapping
    public String configValue() {
        LOGGER.error("this is config value controller : " + configValue);
        return configValue;
    }

}
