package com.example.cms.responseDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserResponse {
	private int userId;
	private String userName;
	private String userEmail;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedsAt;
	
}
