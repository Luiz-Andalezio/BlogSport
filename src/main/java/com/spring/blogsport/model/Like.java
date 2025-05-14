package com.spring.blogsport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many likes to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many likes to one post
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
