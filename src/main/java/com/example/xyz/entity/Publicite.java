package com.example.xyz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Publicite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "the title is required")
    @NotEmpty
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_offre")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;
}
