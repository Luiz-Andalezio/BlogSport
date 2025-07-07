package com.spring.blogsport.service;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.repository.CategoryRepository;
import com.spring.blogsport.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Creates a category
    public Category createCategory(Category category) {
        if (category.getName() == null || category.getName().isBlank())
            throw new IllegalArgumentException("Category name is required");
        if (categoryRepository.findAll().stream().anyMatch(c -> c.getName().equalsIgnoreCase(category.getName())))
            throw new IllegalArgumentException("Category name already exists");
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
        if (updated.getName() == null || updated.getName().isBlank())
            throw new IllegalArgumentException("Category name is required");
        if (!category.getName().equalsIgnoreCase(updated.getName()) &&
            categoryRepository.findAll().stream().anyMatch(c -> c.getName().equalsIgnoreCase(updated.getName())))
            throw new IllegalArgumentException("Category name already exists");
        category.setName(updated.getName());
        return categoryRepository.save(category);
    }

    // Deletes a category
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id))
            throw new IllegalArgumentException("Category not found");
        categoryRepository.deleteById(id);
    }

    // Search the 5 most recent posts for each category
    /*@Autowired
    private PostService postService;

    public List<Category> getAllCategoriesWithRecentPosts() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            List<Post> posts = postService.getRecentPostsByCategory(category.getId());
            category.setPosts(posts);
        }
        return categories;
    }*/

    @Autowired
    private PostRepository postRepository;

    public List<Category> getAllCategoriesWithRecentPosts() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            List<Post> posts = postRepository.findTop5ByCategoryOrderByCreatedAtDesc(category);
            category.setRecentPosts(posts);
        }
        return categories;
    }

    public List<Category> searchCategories(String keyword, Integer limit) {
        if (limit != null && limit == 3) {
            return categoryRepository.findTop3ByNameContainingIgnoreCaseOrderByNameAsc(keyword);
        } else {
            return categoryRepository.searchByKeyword(keyword);
        }
    }
}
