package com.github.amunizma.quizz.rest.game.util;

import java.time.format.DateTimeFormatter;

public class Constant {
	
	
	private Constant(){}
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/** MESAGES HTTP */
	public static final String LEVEL_BAD_REQUEST = "the attribute level is required";
	public static final String DIFFICULTY_BAD_REQUEST = "the attribute difficulty is required";	
	public static final String QUESTION_BAD_REQUEST = "there must be at least one question";
	public static final String CORRECT_ANSWER_BAD_REQUEST = "there must be at least one correctAnswers";
	public static final String WRONG_ANSWER_BAD_REQUEST = "there must be at least one wrongAnswers";
	
	/** ERROR */
	public static final String BAD_REQUEST = "Bad Request";
	public static final String NOT_FOUND = "Not Found";
	public static final String BAD_GATEWAY = "Bad Gateway";
	public static final String CONFLICT = "Conflict";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

}
