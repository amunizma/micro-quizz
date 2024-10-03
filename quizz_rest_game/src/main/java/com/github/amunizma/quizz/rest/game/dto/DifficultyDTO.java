package com.github.amunizma.quizz.rest.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DifficultyDTO {
	private String difficulty;
	private Integer number ;
}