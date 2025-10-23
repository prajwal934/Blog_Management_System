package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestDto.BlogRequest;
import com.example.cms.responseDto.BlogResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest);

	ResponseEntity<Boolean> fetchByTitle(String title);

	ResponseEntity<ResponseStructure<BlogResponse>> findById(int blogId);

	ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(int blogId, BlogRequest blogRequest);


}
