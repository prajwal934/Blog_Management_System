package com.example.cms.requestDto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {

	@NotNull(message = "The title shouldn't be null")
	@Pattern(regexp = "^[a-zA-Z]+$\r\n" , message = "Title supports only alphabets")
	private String title;
	@NotNull
	private String topics;
	private String about;

	public String[] getTopics() {
		return topics.split(",");
	}
}
