package com.example.xyz.service;

import org.springframework.stereotype.Service;

import com.example.xyz.entity.Publicite;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.PubliciteRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

@Service
public class PubliciteService {

    @Autowired
    PubliciteRepository publiciteRepository;

    @Autowired
    UserRepository userRepository;

    public Publicite addPublicite(Publicite publicite, Long userId) {
        // TODO Auto-generated method stub
        Optional<User> oUser = userRepository.findById(userId);
        if (oUser.isPresent()) {
            if (oUser.get().getRoles().contains(RoleName.ROLE_ADMIN)) {
                publicite.setUser(userRepository.findById(userId).get());
                return publiciteRepository.save(publicite);
            } else
                throw new ResourceNotFoundException("You don't have permission on this action");
        } else
            throw new ResourceNotFoundException(("This user " + userId + " not found"));
    }

    public List<Publicite> findAllPublicite() {
        return (List<Publicite>) publiciteRepository.findAll();
    }

    public void deletePublicite(Long id) {

        publiciteRepository.deleteById(id);

    }

    public Publicite getPublicite(Long id) {

        return publiciteRepository.findById(id).orElse(null);
    }

    public Publicite updatePublicite(Publicite publicite, Long publiciteId) {
        Optional<Publicite> oPublicite = publiciteRepository.findById(publiciteId);
        if (oPublicite.isPresent()) {
            return publiciteRepository.save(publicite);
        } else
            throw new ResourceNotFoundException("publiciteId " + publiciteId + " not found");

    }
}
