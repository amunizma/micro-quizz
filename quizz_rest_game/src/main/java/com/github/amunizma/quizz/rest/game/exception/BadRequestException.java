package com.github.amunizma.quizz.rest.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2860342663451181279L;

	public BadRequestException(String msg) {
        super(msg);
    }
}