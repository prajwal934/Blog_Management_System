package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.responseDto.ContributionPanelResponse;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContributionPanelServiceImpl implements ContributionPanelService {

	private UserRepository userRepository;
	private ContributionPanelRepository contributionPanelRepository;
	private BlogRepository blogRepository;
	private ResponseStructure<ContributionPanelResponse> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributor(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(owner -> {
			return contributionPanelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Failed to add");
				return userRepository.findById(userId).map(contributor -> {
					if(! panel.getUsers().contains(contributor)) {
						panel.getUsers().add(contributor);
						contributionPanelRepository.save(panel);
					}
					return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("Contributor added").setData(mapToContributorResponse(panel)));
				}).orElseThrow(() -> new UserNotFoundByIdException("invalid userID"));

			}).orElseThrow(() -> new PanelNotFoundByIdException("invalid panelId"));
		}).get();
	}

	private ContributionPanelResponse mapToContributorResponse(ContributionPanel panel) {
		return ContributionPanelResponse.builder().panelId(panel.getPanelId()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeContributor(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(owner -> {
			return contributionPanelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Failed to add");
				return userRepository.findById(userId).map(contributor -> {
					panel.getUsers().remove(contributor);
					contributionPanelRepository.save(panel);
					return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("Contributor removed").setData(mapToContributorResponse(panel)));
				}).orElseThrow(() -> new UserNotFoundByIdException("invalid userID"));

			}).orElseThrow(() -> new PanelNotFoundByIdException(""));
		}).get();
	}
}
