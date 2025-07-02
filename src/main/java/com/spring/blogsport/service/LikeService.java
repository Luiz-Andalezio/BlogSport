package com.spring.blogsport.service;

import com.spring.blogsport.model.Like;
import com.spring.blogsport.repository.LikeRepository;
import com.spring.blogsport.repository.PostRepository;
import com.spring.blogsport.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository,
            PostRepository postRepository,
            UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    // função que adiciona ou remove um like
    public Like addAndRemoveLike(Long postId, Long userId) {
        Optional<Like> existing = likeRepository.findByPostIdAndUserId(postId, userId);
        if (existing.isPresent()) {
            System.out.println("Removendo like para postId: " + postId + ", userId: " + userId);
            removeLike(postId, userId);
            return null;
        } else {
            System.out.println("Adicionando like para postId: " + postId + ", userId: " + userId);
            return addLike(postId, userId);
        }
    }


    // remove um like existente
    public void removeLike(Long postId, Long userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }


    // Adiciona um novo like
    public Like addLike(Long postId, Long userId) {
        Like like = new Like();
        like.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found")));
        like.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        return likeRepository.save(like);
    }


    // esse é o contador dos likes 
    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}

