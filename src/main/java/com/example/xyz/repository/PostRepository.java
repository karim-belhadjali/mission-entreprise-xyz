package com.example.xyz.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.xyz.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByAuthor_userId(Long userId);

    @Query(value = "SELECT * from Post p where p.title like %:keyword% OR p.content like %:keyword%", nativeQuery = true)
    public List<Post> search(Pageable pageable, @Param("keyword") String keyword);

}