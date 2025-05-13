package com.spring.blogsport.repository;

import com.spring.blogsport.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsportRepository extends JpaRepository<Post, Long> {
}