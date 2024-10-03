package com.github.amunizma.quizz.rest.game.entity;

import java.io.Serializable;
import java.util.List;

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
@Document(collection = "game")
public class GameEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3146346613659559460L;
	
	/**
	 * The unique identifier for the question.
	 */
	@Id
    private String gameId;
	
	/**
	 * The level of the question.
	 */
	private String level;
	private List<QuestionGameEntity> questionsId;
	
}
