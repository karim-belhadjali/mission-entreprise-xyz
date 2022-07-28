package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.xyz.entity.Offer;
import com.example.xyz.repository.OfferRepository;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public ResponseEntity<?> createOffer(Offer offer, Long userId) {

        offerRepository.save(offer);
        return ResponseEntity.ok("Offer created with Succes!");
    }

    @Autowired
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer findById(long offerId) {
        return offerRepository.findById(offerId).get();
    }

    public ResponseEntity<?> update(long offerId, @Valid Offer offerRequest) {

        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isPresent()) {
            Offer offer = offerOptional.get();
            offer.setDescription(offerRequest.getDescription());
            offer.setOfferType(offerRequest.getOfferType());
            offerRepository.save(offer);
            return ResponseEntity.ok("Updated with Succes");

        } else
            return new ResponseEntity<>(("This offer does not exists"), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> detele(Long offerId) {
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isPresent()) {
            Offer offer = offerOptional.get();
            offerRepository.delete(offer);
            return ResponseEntity.ok().build();

        } else
            throw new ResourceNotFoundException("OfferId " + offerId + " not found");
    }

}
