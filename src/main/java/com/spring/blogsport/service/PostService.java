package com.spring.blogsport.service;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.model.Category;
import com.spring.blogsport.repository.PostRepository;
import com.spring.blogsport.repository.UserRepository;
import com.spring.blogsport.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Creates a post for a user and category
    public Post createPost(Post post, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        post.setUser(user);
        post.setCategory(category);
        return postRepository.save(post);
    }

    // Returns all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Returns all posts by user
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    // Returns all posts by category
    public List<Post> getPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    // Returns a single post by ID
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // Updates a post
    public Post updatePost(Long id, Post updated) {
        Post post = getPostById(id);
        post.setTitle(updated.getTitle());
        post.setContent(updated.getContent());
        post.setImageUrl(updated.getImageUrl());
        return postRepository.save(post);
    }

    // Deletes a post
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
