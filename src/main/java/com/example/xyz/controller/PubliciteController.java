package com.example.xyz.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Publicite;
import com.example.xyz.service.PubliciteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/publicite")
public class PubliciteController {

    @Autowired
    PubliciteService publiciteService;

    @PostMapping("/addNewPublicite/{user_id}")
    @ResponseBody
    public Publicite addCollab(@Valid @RequestBody Publicite publicite, @PathVariable Long user_id) {

        return publiciteService.addPublicite(publicite, user_id);
    }

    @DeleteMapping("/deletePublicite/{publiciteId}")
    @ResponseBody
    public ResponseEntity<?> deletePublicite(@PathVariable("publiciteId") Long id) {
        publiciteService.deletePublicite(id);
        return new ResponseEntity<>(
                "Publicite deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/findAllPublicite")
    @ResponseBody
    public List<Publicite> findAllPublicite() {
        List<Publicite> listPublicite = publiciteService.findAllPublicite();
        return listPublicite;
    }

    @GetMapping("/findById/{publiciteId}")
    @ResponseBody
    public Publicite getPubliciteById(@PathVariable("publiciteId") Long publiciteId) {
        return publiciteService.getPublicite(publiciteId);
    }

    @PutMapping("/updatePublicite/{publiciteId}")
    @ResponseBody
    public Publicite updatePublicite(@Valid @RequestBody Publicite publicite, @PathVariable long publiciteId) {

        return publiciteService.updatePublicite(publicite, publiciteId);
    }
}
