package com.billion.quiz.service;

import com.billion.quiz.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findById(String categoryId);

    List<CategoryDto> findAllCategory(CategoryDto categoryDto);

    void delete(String categoryId);

    CategoryDto update(String categoryId, CategoryDto categoryDto);

    CategoryDto create(CategoryDto categoryDto);

}
