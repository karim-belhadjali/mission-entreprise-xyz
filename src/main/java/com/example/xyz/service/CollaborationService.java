package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.xyz.entity.Collaboration;
import com.example.xyz.entity.Role;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.CollaborationRepository;

@Service
public class CollaborationService implements ICollaborationService {

    @Autowired
    CollaborationRepository collaborationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Collaboration addCollaboration(Collaboration collaboration, Long userId) {
        Optional<User> oUser = userRepository.findById(userId);
        if (oUser.isPresent()) {
            if (oUser.get().getRoles().contains(RoleName.ROLE_ADMIN)) {
                collaboration.setUser(userRepository.findById(userId).get());
                return collaborationRepository.save(collaboration);
            } else
                throw new ResourceNotFoundException("You don't have permission on this action");
        } else
            throw new ResourceNotFoundException(("This user " + userId + " not found"));

    }

    @Override
    public List<Collaboration> findAllCollaboration() {
        return (List<Collaboration>) collaborationRepository.findAll();
    }

    @Override
    public void deleteCollaboration(Long id) {

        collaborationRepository.deleteById(id);

    }

    @Override
    public Collaboration getCollaboration(Long id) {

        return collaborationRepository.findById(id).orElse(null);
    }

    @Override
    public Collaboration updateCollaboration(Collaboration c, Long collaborationId) {
        Optional<Collaboration> collab = collaborationRepository.findById(collaborationId);
        if (collab.isPresent()) {
            Collaboration collaboration = collab.get();
            collaboration.setDescription(c.getDescription());
            collaboration.setCollaborator(c.getCollaborator());

            return collaborationRepository.save(collaboration);
        } else
            throw new ResourceNotFoundException("collaborationId " + collaborationId + " not found");

    }
}
