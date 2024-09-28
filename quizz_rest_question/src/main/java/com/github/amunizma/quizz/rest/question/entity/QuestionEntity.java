package com.github.amunizma.quizz.rest.question.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity question = (QuestionEntity) o;
        return Objects.equals(questionId, question.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("{");
    	
    	if(this.questionId != null)sb.append("questionId='").append(questionId).append('\'');
        if(this.level != null)sb.append(", level='").append(level).append('\'');
        if(this.difficulty != null)sb.append(", difficulty='").append(difficulty).append('\'');
        if(this.questions != null)sb.append(", questions='").append(questions).append('\'');
        if(this.correctAnswers != null)sb.append(", correctAnswers='").append(correctAnswers).append('\'');
        if(this.wrongAnswers != null)sb.append(", wrongAnswers='").append(wrongAnswers).append('\'');
        if(this.used != null)sb.append(", ages='").append(used).append('\'');
        sb.append('}');
    	
        return sb.toString();
    }
	
}
