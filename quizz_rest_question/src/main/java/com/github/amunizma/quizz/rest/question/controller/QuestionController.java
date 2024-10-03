package com.github.amunizma.quizz.rest.question.controller;

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

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.service.QuestionService;
import com.github.amunizma.quizz.rest.question.validation.ValidUUID;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = ("/question"))
public class QuestionController {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService service;

	public QuestionController(QuestionService service) {
		this.service = service;
	}

	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionBaseDTO request) {
		logger.info("Received request to add a new question: {}", request.toString());
		QuestionDTO responseDTO = service.createQuestion(request);
		logger.info("Question '{}' successfully added.", responseDTO.toString());
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@ValidUUID @PathVariable String id) {
		logger.info("Received request to get question: {}", id);
		QuestionDTO questionDTO = service.getQuestion(id);
		logger.info("Question '{}' successfully getted.", questionDTO.toString());
		return new ResponseEntity<>(questionDTO, HttpStatus.OK);
    }


}