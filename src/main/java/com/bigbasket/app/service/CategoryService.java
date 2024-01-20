package com.bigbasket.app.service;

import com.bigbasket.app.model.Category;
import com.bigbasket.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Integer categoryId, Category updatedCategory) {
        Category category = categoryRepository.getReferenceById(updatedCategory.getId());

        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());
        category.setImageUrl(updatedCategory.getImageUrl());

        categoryRepository.save(category);
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
}
