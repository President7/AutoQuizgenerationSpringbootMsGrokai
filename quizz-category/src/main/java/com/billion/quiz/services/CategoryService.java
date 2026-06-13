package com.billion.quiz.services;

import com.billion.quiz.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto getById(String id);
    public List<CategoryDto> getAllCategory();
    public void deleteCategory(String id);
    public CategoryDto updateCategory(String id, CategoryDto categoryDto);

}
