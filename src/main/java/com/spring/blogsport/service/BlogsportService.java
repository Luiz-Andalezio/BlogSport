package com.spring.blogsport.service;

import java.util.List;

import com.spring.blogsport.model.Post;

public interface BlogsportService {
    List<Post> findAll();
    Post findById(Long id);
    Post save(Post post);
}

/*
package com.spring.blogsport.service;

import java.util.List;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import com.spring.blogsport.model.Post;

@Service
public class BlogsportService {

    public List<Post> findAll() {
        return Arrays.asList(
            new Post("Primeiro Post", "Conteúdo do primeiro post."),
            new Post("Segundo Post", "Conteúdo do segundo post.")
        );
    }
}
*/
