package com.example.xyz.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Offer;
import com.example.xyz.entity.OfferDTO;
import com.example.xyz.repository.OfferRepository;
import com.example.xyz.service.OfferService;
import com.example.xyz.utilities.FileUtilities;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    ModelMapper modelMapper;

    @PostMapping("/addOffer/{userId}")
    @RequestMapping(value = "/addOffer/{userId}", method = RequestMethod.POST, headers = "Accept=application/json", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, produces = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<?> addOffer(@Valid @RequestBody @ModelAttribute OfferDTO offerDTO,
            @PathVariable long userId) {
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        String FileName = offerDTO.getImage().getOriginalFilename();
        FileUtilities.saveArticleImage(FileName, offerDTO.getImage());
        offer.setFilePath("C:/images/offres/" + FileName);
        return offerService.createOffer(offer, userId);
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
