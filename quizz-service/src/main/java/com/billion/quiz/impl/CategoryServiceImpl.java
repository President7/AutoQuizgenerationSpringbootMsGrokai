package com.billion.quiz.impl;

import com.billion.quiz.dto.CategoryDto;
import com.billion.quiz.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Primary
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final WebClient webClient;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(WebClient.Builder webClient, ModelMapper modelMapper) {
        //this.webClient = webClient;
        this.webClient = webClient.baseUrl("lb://QUIZZ-CATEGORY").build();
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDto findById(String categoryId) {

        try {
            CategoryDto category = this.webClient.get()
                    .uri("/quiz/category/{categoryId}", categoryId)
                    .retrieve()
                    .bodyToMono(CategoryDto.class)
                    .block();
            return category;

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                logger.error(" Internal Server Error");
            else if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                logger.error(" Resource NOt found Exception");
            else if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST))
                logger.error(" Bad Request Error");

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CategoryDto> findAllCategory(CategoryDto categoryDto) {
        List<CategoryDto> getCategory = this.webClient.get()
                .uri("/quiz/category")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();

        return getCategory;
    }

    @Override
    public void delete(String categoryId) {
        this.webClient.delete()
                .uri("/quiz/category/{categoryId}", categoryId)
                .retrieve()
                .toBodilessEntity()
                .block();
       // return updateCategory;
    }

    @Override
    public CategoryDto update(String categoryId, CategoryDto categoryDto) {
        try {

            CategoryDto updateCategory = this.webClient.put()
                    .uri("/quiz/category/{categoryId}", categoryId)
                    .bodyValue(categoryDto)
                    .retrieve()
                    .bodyToMono(CategoryDto.class)
                    .block();
            return updateCategory;
        }catch (WebClientResponseException e) {
            if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                logger.error(" Internal Server Error");
            else if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                logger.error(" Resource NOt found Exception");
            else if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST))
                logger.error(" Bad Request Error");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        try {
            CategoryDto category = this.webClient.post()
                    .uri("/quiz/category")
                    .bodyValue(categoryDto)
                    .retrieve()
                    .bodyToMono(CategoryDto.class)
                    .block();
            return category;

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                logger.error(" Internal Server Error");
            else if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                logger.error(" Resource NOt found Exception");
            else if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST))
                logger.error(" Bad Request Error");
            e.printStackTrace();
        }

        return null;
    }
}
