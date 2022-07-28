package com.example.xyz.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Collaboration;
import com.example.xyz.entity.Offer;
import com.example.xyz.entity.OfferDTO;
import com.example.xyz.entity.User;
import com.example.xyz.enums.OfferType;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.CollaborationRepository;
import com.example.xyz.repository.OfferRepository;
import com.example.xyz.service.OfferService;
import com.example.xyz.utilities.FileUtilities;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;
    ModelMapper modelMapper;

    @RequestMapping(value = "/addOffer/{collaborationId}", method = RequestMethod.POST)
    public ResponseEntity<?> addOffer(@RequestBody @ModelAttribute OfferDTO offerDTO,
            @PathVariable long collaborationId) {
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        String FileName = offerDTO.getFilePath().getOriginalFilename();
        FileUtilities.saveArticleImage(FileName, offerDTO.getFilePath());
        offer.setFilePath("C:/images/offres/" + FileName);
        return offerService.createOffer(offer, collaborationId);
    }

    @GetMapping("/offers")
    public List<Offer> getAllOffers() {
        return offerService.findAll();
    }

    @PatchMapping("/offers/{offerId}")
    public ResponseEntity<?> updateOffer(@PathVariable long offerId, @Valid @RequestBody Offer offerRequest) {
        return offerService.update(offerId, offerRequest);
    }

}
