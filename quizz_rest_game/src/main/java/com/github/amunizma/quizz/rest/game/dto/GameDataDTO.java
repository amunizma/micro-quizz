package com.github.amunizma.quizz.rest.game.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameDataDTO {
	private String level;
	private List<DifficultyDTO> amount;
}
