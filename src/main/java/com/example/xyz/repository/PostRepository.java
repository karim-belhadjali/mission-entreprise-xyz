package com.example.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xyz.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByAuthor_id(Long userId);

    public List<Post> findByTitleContaining(String keyword);

    public List<Post> findByContentContaining(String keyword);

    public List<Post> findTopByNbrOfStarsOrderByNbrOfStarsDesc();

    public List<Post> findTopByNbrOfViewsOrderByNbrOfViewsDesc();
}