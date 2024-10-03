package com.github.amunizma.quizz.rest.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class BadGatewayException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8699970030068420346L;

	public BadGatewayException(String msg) {
        super(msg);
    }
}