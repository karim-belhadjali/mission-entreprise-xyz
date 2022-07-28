package com.example.xyz.entity;

import javax.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Collaboration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "description is required")
    @NotEmpty
    private String description;

    @NotNull(message = "collaborator Name is required")
    @NotEmpty
    private String collaborator;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "creator_id")
    private User user;
}
