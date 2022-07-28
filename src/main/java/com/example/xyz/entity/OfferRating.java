package com.example.xyz.entity;

import javax.persistence.*;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
public class OfferRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(min = 1, max = 5, message = "Rate must be between 1 to 5")
    private int rate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "offre_id")
    private Offer offre;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "creator_id")
    private User user;

}
