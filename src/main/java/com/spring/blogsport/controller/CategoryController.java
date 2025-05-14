package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@PreAuthorize("hasAuthority('ADMIN')") // Only admin users can access
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List all categories
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    // Form for new category
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";
    }

    // Create category
    @PostMapping
    public String createCategory(@ModelAttribute @Valid Category category,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "categoryForm";
        }
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categoryForm";
    }

    // Update category
    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute @Valid Category category,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "categoryForm";
        }
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }

    // Delete category
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
