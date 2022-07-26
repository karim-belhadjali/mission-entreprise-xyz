package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @OneToOne
    @JoinColumn(name="postComment_id", nullable=false)
    private PostComment postComment;

    @ManyToOne()
    private User author;
}
