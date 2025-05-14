package com.spring.blogsport.service;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Creates a category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Lists all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Finds a category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // Updates a category
    public Category updateCategory(Long id, Category updated) {
        Category category = getCategoryById(id);
        category.setName(updated.getName());
        return categoryRepository.save(category);
    }

    // Deletes a category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
