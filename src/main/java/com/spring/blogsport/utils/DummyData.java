package com.spring.blogsport.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.repository.BlogsportRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DummyData {

    @Autowired
    BlogsportRepository blogsportRepository;

    @PostConstruct
    public void savePosts() {
        List<Post> postList = new ArrayList<>();

        Post post1 = new Post();
        post1.setAuthor("Teste");
        post1.setDate(LocalDate.now());
        post1.setTitle("Teste");
        post1.setText("Teste");

        Post post2 = new Post();
        post2.setAuthor("Teste");
        post2.setDate(LocalDate.now());
        post2.setTitle("Teste");
        post2.setText("Teste");

        postList.add(post1);
        postList.add(post2);

        for (Post post : postList) {
            Post postSaved = blogsportRepository.save(post);
            System.out.println("Post salvo: " + postSaved.getId());
        }
    }
}