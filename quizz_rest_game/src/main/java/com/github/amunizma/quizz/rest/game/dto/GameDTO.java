package com.github.amunizma.quizz.rest.game.dto;

import java.util.List;

import com.github.amunizma.quizz.rest.game.util.Constant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class GameDTO {

	/**
	 * @NotBlank: se usa en cadenas; y comprueba que no sean nulas, ni vacía, ni contenga solo espacios en blancos
	 */
	@NotBlank(message = Constant.LEVEL_BAD_REQUEST)
	private String level;
	
	/**
	 * @NotBlank: se usa en cadenas, colecciones, arrays y mapas; y comprueba que no sea nulo, ni vacío
	 */
	@NotEmpty(message = Constant.QUESTION_BAD_REQUEST)
	private List<QuestionGameDTO> questions;
}
