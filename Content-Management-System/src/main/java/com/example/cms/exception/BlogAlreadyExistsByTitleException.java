package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class BlogAlreadyExistsByTitleException extends RuntimeException {

	private String message;
}
