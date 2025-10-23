package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestDto.UserRequest;
import com.example.cms.responseDto.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

}
