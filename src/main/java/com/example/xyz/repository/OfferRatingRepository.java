package com.example.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xyz.entity.OfferRating;

@Repository
public interface OfferRatingRepository extends JpaRepository<OfferRating, Long> {

}
