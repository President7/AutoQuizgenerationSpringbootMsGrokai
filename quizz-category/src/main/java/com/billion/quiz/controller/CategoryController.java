package com.billion.quiz.controller;

import com.billion.quiz.dto.CategoryDto;
import com.billion.quiz.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz/category")
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto categoryCreated = categoryService.createCategory(categoryDto);
        LOGGER.info("Category Created successFully..");
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable String id) {
        CategoryDto byId = categoryService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @DeleteMapping({"/id"})
    public ResponseEntity<CategoryDto> deleteById(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.updateCategory(id, categoryDto);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

}
