package com.example.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xyz.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    /* public List<PostComment> findByPost_id(Long postId);

    public List<PostComment> findByPostComment_id(Long postCommentId);

    public List<PostComment> findByAuthor_id(Long userId); */
}