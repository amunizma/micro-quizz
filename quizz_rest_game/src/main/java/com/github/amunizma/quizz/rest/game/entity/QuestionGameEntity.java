package com.github.amunizma.quizz.rest.game.entity;

import lombok.AllArgsConstructor;
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
public class QuestionGameEntity {
	
	/**
	 * The questionId of the question.
	 */
	private String questionId;
	
	/**
	 * The difficulty for the question.
	 */
    private String difficulty;
    
    /**
	 * The correct for the question.
	 */
    private String correct;
	
}
