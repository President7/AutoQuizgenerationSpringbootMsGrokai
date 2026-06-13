package com.billion.qus.functions;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AiConfigDebug {

    private static final Logger LOGGER= LoggerFactory.getLogger(AiConfigDebug.class);
    @Value("${spring.ai.openai.chat.options.model:}")
    private String model;

    @PostConstruct
    public  void checkModel(){
        LOGGER.info(" model : " + model);
    }
}
