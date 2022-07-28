package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.xyz.entity.Collaboration;
import com.example.xyz.entity.Offer;
import com.example.xyz.entity.User;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.CollaborationRepository;
import com.example.xyz.repository.OfferRepository;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollaborationRepository collaborationRepository;

    public ResponseEntity<?> createOffer(Offer offer, Long userId, Long collaborationId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> oAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (oAuthor.isPresent()) {
            User author = oAuthor.get();
            offer.setUser(author);
        }

        Optional<Collaboration> optional = collaborationRepository.findById(collaborationId);
        if (optional.isPresent()) {
            Collaboration collaboration = optional.get();
            offer.setCollaboration(collaboration);
        }
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
