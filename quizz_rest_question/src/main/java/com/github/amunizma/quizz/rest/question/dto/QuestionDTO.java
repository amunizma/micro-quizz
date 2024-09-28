package com.github.amunizma.quizz.rest.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuestionDTO extends QuestionBaseDTO {
	
	private String questionId;
	
	private Integer used = 0;

}