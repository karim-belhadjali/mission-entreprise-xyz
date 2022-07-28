package com.example.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xyz.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
