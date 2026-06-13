package com.billion.quiz.services.impl;

import com.billion.quiz.config.ResourceNotFoundException;
import com.billion.quiz.entities.Category;
import org.modelmapper.ModelMapper;
import com.billion.quiz.dto.CategoryDto;
import com.billion.quiz.repository.CategoryRepository;
import com.billion.quiz.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        LOGGER.info(" inside create category methods");
        Category createCategory = modelMapper.map(categoryDto, Category.class);
        createCategory.setId(UUID.randomUUID().toString());
        Category saveCategory = categoryRepository.save(createCategory);

        LOGGER.info(" Category creation done before return ");
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getById(String id) {
        LOGGER.info(" inside create category methods " + id);
        Category categoryFindById = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with this Id : " + id));
        return modelMapper.map(categoryFindById, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> all = categoryRepository.findAll();
        LOGGER.error(" category : " + all.toString());
        return all.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }

    @Override
    public void deleteCategory(String id) {
        Category categoryFindById = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with this Id : " + id));
        categoryRepository.delete(categoryFindById);
        LOGGER.error("Deleted successFully by Id " + id);
    }

    @Override
    public CategoryDto updateCategory(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The id is not found : " + id));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setActive(categoryDto.isActive());
        Category updateCategory = categoryRepository.save(category);
        LOGGER.error("Category Updated SuccessFully : " + updateCategory.toString());
        return modelMapper.map(updateCategory, CategoryDto.class);
    }
}
