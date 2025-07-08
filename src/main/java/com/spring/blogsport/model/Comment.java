package com.spring.blogsport.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment text is required")
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Many comments to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @jakarta.validation.constraints.NotNull(message = "User is required")
    private User user;

    // Many comments to one post
    @ManyToOne
    @JoinColumn(name = "post_id")
    @jakarta.validation.constraints.NotNull(message = "Post is required")
    private Post post;

    // Optional: threaded replies (self-referencing comment replies)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentLike> likes = new ArrayList<>();

    public int getLikeCount(){
        return likes != null ? likes.size():0;
    }

    public boolean isLikedByUser(User user){
        if(user == null || likes == null) return false;
        return likes.stream().anyMatch(like -> like.getUser().getId().equals(user.getId()));
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
