package com.example.xyz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.xyz.entity.Collaboration;

@Repository
public interface CollaborationRepository extends CrudRepository<Collaboration, Long> {

    Optional<Collaboration> findBycollaborator(String collaborator);
}
