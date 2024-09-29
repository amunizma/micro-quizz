package com.github.amunizma.quizz.rest.question.dto;

import java.util.List;

import com.github.amunizma.quizz.rest.question.util.Constant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class QuestionBaseDTO {
	
	/**
	 * @NotBlank: se usa en cadenas; y comprueba que no sean nulas, ni vacía, ni contenga solo espacios en blancos
	 */
	@NotBlank(message = Constant.LEVEL_BAD_REQUEST) //Cadena no nula ni vacia
	private String level;
	
	@NotBlank(message = Constant.DIFFICULTY_BAD_REQUEST)
	private String difficulty;
	
	/**
	 * @NotBlank: se usa en cadenas, colecciones, arrays y mapas; y comprueba que no sea nulo, ni vacío
	 */
	@NotEmpty(message = Constant.QUESTION_BAD_REQUEST) //Lista no nula ni vacia
	private List<String> questions;
	
	@NotEmpty(message = Constant.CORRECT_ANSWER_BAD_REQUEST)
	private List<String> correctAnswers;
	
	@NotEmpty(message = Constant.WRONG_ANSWER_BAD_REQUEST)
	private List<String> wrongAnswers;

}