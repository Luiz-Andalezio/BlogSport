package com.spring.blogsport.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.repository.BlogsportRepository;
import com.spring.blogsport.service.BlogsportService;

@Service
public class BlogsportServiceImpl implements BlogsportService {

    @Autowired
    BlogsportRepository blogsportRepository;

    @Override
    public List<Post> findAll() {
        return blogsportRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return blogsportRepository.findById(id).get();
    }

    @Override
    public Post save(Post post) {
        return blogsportRepository.save(post);
    }
}