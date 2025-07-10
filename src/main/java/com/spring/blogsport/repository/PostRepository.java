package com.spring.blogsport.repository;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Returns all posts by user ID
    List<Post> findByUserId(Long userId);

    // Returns all posts by category ID
    List<Post> findByCategoryId(Long categoryId);

    // Retuns top5 posts by category ID order by created at descending
    List<Post> findTop5ByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    // Retuns top5 posts by category order by created at descending
    List<Post> findTop5ByCategoryOrderByCreatedAtDesc(Category category);

    // Returns title or content (case insensitive)
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.createdAt DESC")
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    // Returns top3 by title
    List<Post> findTop3ByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(String title, String content);

    List<Post> findByTitleContainingIgnoreCase(String title);
}
