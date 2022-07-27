package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int nbrOfStars;
    private int nbrOfViews;

    @OneToMany(mappedBy="post")
    private List<PostComment> comments;

    @ManyToOne()
    private User author;

}
