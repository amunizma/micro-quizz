package com.github.amunizma.quizz.rest.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8959645789518888248L;

	public InternalServerErrorException(String msg) {
        super(msg);
    }
}