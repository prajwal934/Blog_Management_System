package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.responseDto.ContributionPanelResponse;
import com.example.cms.utility.ResponseStructure;

public interface ContributionPanelService {

	ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributor(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeContributor(int userId, int panelId);


}
