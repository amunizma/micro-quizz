package com.github.amunizma.quizz.rest.question.service;

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;

public interface QuestionService {
	public QuestionDTO createQuestion(QuestionBaseDTO request);
	public QuestionDTO getQuestion(String questionId);
}
