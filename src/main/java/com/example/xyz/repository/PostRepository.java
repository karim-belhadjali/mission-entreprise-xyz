package com.example.xyz.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.xyz.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByAuthor_id(Long userId);

    @Query("SELETCT * from Post where p.title like %:name% OR p.content like %:name%")
    public List<Post> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @Query("SELETCT * from Post ORDER BY nbrOfStars desc UNION SELETCT * from Post ORDER BY nbrOfViews desc")
    public List<Post> findFeed(Pageable pageable);
}