package com.spring.blogsport.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.CategoryRepository;
import com.spring.blogsport.repository.PostRepository;
import com.spring.blogsport.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DummyData {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void saveDummyData() {
        //Categories
        Category futebol = categoryRepository.findByName("Futebol")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Futebol").build()));
        Category basquete = categoryRepository.findByName("Basquete")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Basquete").build()));

        //Users
        User admin = userRepository.findByEmail("admin@blogsport.com")
                .orElseGet(() -> userRepository.save(User.builder()
                        .name("Admin")
                        .email("admin@blogsport.com")
                        .password(passwordEncoder.encode("admin123"))
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .role(User.Role.ADMIN)
                        .build()));

        User user = userRepository.findByEmail("user@blogsport.com")
                .orElseGet(() -> userRepository.save(User.builder()
                        .name("Usuário")
                        .email("user@blogsport.com")
                        .password(passwordEncoder.encode("user123"))
                        .birthDate(LocalDate.of(2000, 5, 10))
                        .role(User.Role.USER)
                        .build()));

        //Posts
        if (postRepository.findAll().isEmpty()) {
            postRepository.save(Post.builder()
                    .title("Primeiro post de Futebol")
                    .content("Conteúdo sobre futebol.")
                    .createdAt(LocalDateTime.now())
                    .user(admin)
                    .category(futebol)
                    .build());

            postRepository.save(Post.builder()
                    .title("Primeiro post de Basquete")
                    .content("Conteúdo sobre basquete.")
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .category(basquete)
                    .build());
        }
    }
}