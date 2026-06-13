package com.billion.quiz.service;

import com.billion.quiz.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "QUIZZ-CATEGORY")
public interface CategoryFeignService {
    @GetMapping("/quiz/category")
    List<CategoryDto> findAll();

    @GetMapping("/quiz/category/{id}")
    CategoryDto findById(@PathVariable String id);

    @PostMapping("/quiz/category/createCategory")
    CategoryDto createCategory(@RequestBody CategoryDto categoryDto);

    @PutMapping("/quiz/category/updateCategory/{id}")
    CategoryDto updateCategory(@PathVariable String id, @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/quiz/category/{id}")
    CategoryDto delete(@PathVariable String id);

}
