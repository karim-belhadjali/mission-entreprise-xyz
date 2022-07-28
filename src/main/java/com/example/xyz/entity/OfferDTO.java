package com.example.xyz.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.example.xyz.enums.OfferType;

@Data
public class OfferDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MultipartFile image;

    @NotNull(message = "description is required")
    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaboration_id", nullable = false)
    private Collaboration collaboration;

    @Range(min = 1, max = 5, message = "Rate must be between 1 to 5")
    private int rate;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;
}
