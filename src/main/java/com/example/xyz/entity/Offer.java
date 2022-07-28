package com.example.xyz.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.example.xyz.enums.OfferType;

@Data
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @NotNull(message = "description is required")
    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "collaboration_id", nullable = false)
    private Collaboration collaboration;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;
}
