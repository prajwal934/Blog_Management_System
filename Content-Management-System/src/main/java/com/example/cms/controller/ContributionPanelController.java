package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.responseDto.ContributionPanelResponse;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ContributionPanelController {

	private ContributionPanelService contributionPanelService;
	
	@PutMapping(value = "/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributor(@PathVariable int userId,@PathVariable int panelId){
		return contributionPanelService.addContributor(userId,panelId);
	}
	
	@DeleteMapping(value = "/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeContributor(@PathVariable int userId,@PathVariable int panelId){
		return contributionPanelService.removeContributor(userId,panelId);
	}
	
}
