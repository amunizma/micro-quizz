package com.github.amunizma.quizz.rest.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ServiceUnavailableException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1552214554811277413L;

	public ServiceUnavailableException(String msg) {
        super(msg);
    }
}