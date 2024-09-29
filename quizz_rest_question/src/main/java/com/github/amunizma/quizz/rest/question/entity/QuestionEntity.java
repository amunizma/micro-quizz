package com.github.amunizma.quizz.rest.question.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a question entity in the application.
 * This entity is mapped to a MongoDB collection named "question".
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "question")
public class QuestionEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6020771885338016780L;
	
	/**
	 * The unique identifier for the question.
	 */
	@Id
    private String questionId;
	/**
	 * The level of the question.
	 */
	private String level;
	/**
	 * The difficulty of the question.
	 */
	private String difficulty;
	
	/**
	 * The questions of the question.
	 */
	private List<String> questions;
	/**
	 * The correctAnswers of the question.
	 */
	private List<String> correctAnswers;
	/**
	 * The wrongAnswers of the question.
	 */
	private List<String> wrongAnswers;
	/**
	 * The used of the question.
	 */
	private Integer used;

	
}
