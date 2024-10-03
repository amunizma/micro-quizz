package com.github.amunizma.quizz.rest.game.dto;


import com.github.amunizma.quizz.rest.game.util.Constant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionGameDTO {
	
	@NotBlank(message = Constant.DIFFICULTY_BAD_REQUEST)
	private String questionId;
	
	@NotBlank(message = Constant.DIFFICULTY_BAD_REQUEST)
	private String difficulty;
	
	private String correct = "n/s";

}
