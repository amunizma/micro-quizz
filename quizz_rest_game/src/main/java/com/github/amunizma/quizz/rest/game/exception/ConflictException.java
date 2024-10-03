package com.github.amunizma.quizz.rest.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7010997421912970827L;

	public ConflictException(String msg) {
        super(msg);
    }
}