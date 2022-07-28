package com.example.xyz.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import com.example.xyz.enums.OfferType;

@Data
public class OfferDTO implements Serializable {
    private Long id;

    private MultipartFile filePath;

    private String description;

    private OfferType offerType;

    private Collaboration collaboration;

    private User user;
}
