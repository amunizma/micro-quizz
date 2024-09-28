package com.github.amunizma.quizz.rest.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1065205685552733221L;

	public NotFoundException(String msg) {
        super(msg);
    }
}