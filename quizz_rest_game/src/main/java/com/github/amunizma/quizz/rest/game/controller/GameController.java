package com.github.amunizma.quizz.rest.game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.amunizma.quizz.rest.game.dto.GameDTO;
import com.github.amunizma.quizz.rest.game.service.GameService;
import com.github.amunizma.quizz.rest.game.validation.ValidUUID;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = ("/game"))
public class GameController {
	
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService service;

	public GameController(GameService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> createQuestion(@Valid @RequestBody GameDTO request) {
		logger.info("Received request to add a new game: {}", request.toString());
		GameDTO responseDTO = service.createGame(request);
		logger.info("Game '{}' successfully added.", responseDTO.toString());
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@ValidUUID @PathVariable String id) {
		logger.info("Received request to get question: {}", id);
		GameDTO gameDTO = service.getGame(id);
		logger.info("Question '{}' successfully getted.", gameDTO.toString());
		return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }

}
