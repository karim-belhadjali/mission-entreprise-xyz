package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.xyz.entity.OfferRating;
import com.example.xyz.entity.User;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.OfferRatingRepository;

@Service
public class OfferRatingService {

    @Autowired
    private OfferRatingRepository offerRatingRepository;

    public OfferRating createOfferRating(OfferRating offerRating) {

        return offerRatingRepository.save(offerRating);

    }

    @Autowired
    public List<OfferRating> findAll() {
        return offerRatingRepository.findAll();
    }

    public OfferRating findById(long offerRatingId) {
        Optional<OfferRating> offerRatingOptional = offerRatingRepository.findById(offerRatingId);
        if (offerRatingOptional.isPresent()) {
            return offerRatingOptional.get();
        } else
            throw new ResourceNotFoundException("offerRating Id " + offerRatingOptional.get().getId() + " not found");
    }

    public ResponseEntity<?> update(long offerRatingId, @Valid OfferRating offerRatingRequest) {

        Optional<OfferRating> offerRatingOptional = offerRatingRepository.findById(offerRatingId);
        if (offerRatingOptional.isPresent()) {
            OfferRating offerRating = offerRatingOptional.get();
            offerRating.setRate(offerRatingRequest.getRate());
            offerRatingRepository.save(offerRating);
            return ResponseEntity.ok("Updated with Succes");

        } else
            return new ResponseEntity<>(("This offerRating does not exists"), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> detele(Long offerRatingId) {
        Optional<OfferRating> offerRatingOptional = offerRatingRepository.findById(offerRatingId);
        if (offerRatingOptional.isPresent()) {
            OfferRating offerRating = offerRatingOptional.get();
            offerRatingRepository.delete(offerRating);
            return ResponseEntity.ok().build();

        } else
            throw new ResourceNotFoundException("OfferRatingId " + offerRatingId + " not found");
    }

}
