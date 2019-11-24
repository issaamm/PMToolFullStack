package com.issam.ppmtool.ppmToolProject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueLoginException extends RuntimeException{

	public UniqueLoginException(String message) {
		super(message);
	}
}
