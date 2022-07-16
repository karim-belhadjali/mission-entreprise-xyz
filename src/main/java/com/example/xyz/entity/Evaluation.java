package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="created_by")
    private User createdBy;

    private int evaluation;
}
