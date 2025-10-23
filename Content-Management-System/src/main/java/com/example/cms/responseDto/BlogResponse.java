package com.example.cms.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogResponse {

	private int blogId;
	private String title;
	private String[] topics;
	private String about;
}
