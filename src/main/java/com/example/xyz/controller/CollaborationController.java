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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Collaboration;
import com.example.xyz.service.ICollaborationService;

@RestController
@RequestMapping("/collaboration")
public class CollaborationController {

    @Autowired
    ICollaborationService icollaborationService;

    @PostMapping("/addNewCollaboration/{user_id}")
    @ResponseBody
    public Collaboration addCollaboration(@Valid @RequestBody Collaboration c, @PathVariable Long user_id) {

        return icollaborationService.addCollaboration(c, user_id);
    }

    @DeleteMapping("/deleteCollaboration/{collaborationId}")
    @ResponseBody
    public ResponseEntity<?> deleteCollaboration(@PathVariable("collaborationId") Long id) {
        icollaborationService.deleteCollaboration(id);
        return new ResponseEntity<>(
                "Collaboration deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/findAllCollaboration")
    @ResponseBody
    public List<Collaboration> findAllCollaboration() {
        List<Collaboration> listCollaboration = icollaborationService.findAllCollaboration();
        return listCollaboration;
    }

    @GetMapping("/findById/{collaborationId}")
    @ResponseBody
    public Collaboration getCollaborationById(@PathVariable("collaborationId") Long collaborationId) {
        return icollaborationService.getCollaboration(collaborationId);
    }

    @PutMapping("/updateCollaboration/{collaborationId}")
    @ResponseBody
    public Collaboration updateCollaboration(@Valid @RequestBody Collaboration collaboration,
            @PathVariable long collaborationId) {

        return icollaborationService.updateCollaboration(collaboration, collaborationId);
    }

}
