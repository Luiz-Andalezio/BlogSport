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
