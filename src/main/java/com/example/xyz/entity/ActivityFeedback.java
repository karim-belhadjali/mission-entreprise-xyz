package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ActivityFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;
    private Boolean isFavorite;


    @OneToMany
    @JoinColumn(name="activity_feedback")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name="submitted_by")
    private User submittedBy;
}
