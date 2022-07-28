package com.example.xyz.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.xyz.entity.OfferRating;
import com.example.xyz.service.OfferRatingService;

@RestController
@RequestMapping("/offerRating")
public class OfferRatingController {

    @Autowired
    private OfferRatingService offerRatingService;

    @PostMapping("/addOfferRating")
    public ResponseEntity<?> addOffer(@RequestBody OfferRating offerRating) {
        OfferRating offerRatingLocal = offerRatingService.createOfferRating(offerRating);

        if (offerRatingLocal != null)
            return new ResponseEntity<>(offerRatingLocal, HttpStatus.OK);
        else
            return new ResponseEntity<>(("This OfferRating cannot create"), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/OfferRatings")
    public List<OfferRating> getAllOfferRatings() {
        return offerRatingService.findAll();
    }

    @PatchMapping("/OfferRatings/{offerRatingId}")
    public ResponseEntity<?> updateOffer(@PathVariable long offerRatingId,
            @Valid @RequestBody OfferRating offerRatingRequest) {
        return offerRatingService.update(offerRatingId, offerRatingRequest);
    }

}
