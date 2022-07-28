package com.example.xyz.service;

import java.util.List;

import com.example.xyz.entity.Collaboration;

public interface ICollaborationService {
	public Collaboration addCollaboration(Collaboration c, Long userId);

	public List<Collaboration> findAllCollaboration();

	public void deleteCollaboration(Long id);

	public Collaboration getCollaboration(Long id);

	public Collaboration updateCollaboration(Collaboration c, Long collaborationId);

}
