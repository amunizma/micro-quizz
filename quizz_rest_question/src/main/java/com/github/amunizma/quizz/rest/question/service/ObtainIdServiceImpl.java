package com.github.amunizma.quizz.rest.question.service;

import com.github.amunizma.quizz.rest.question.respository.QuestionRepository;

public class ObtainIdServiceImpl implements ObtainIdService{
	
	private final QuestionRepository questionRepository;

    public ObtainIdServiceImpl(QuestionRepository questionRepository) {
    	this.questionRepository = questionRepository;
    }
	/**
	 * Check if the ID already exists
	 * 
	 * @param uuid ID to check
	 * @return True | False
	 */
	public boolean existUUID(String uuid) {
		boolean exists = false;
		long count = 0;
		if(uuid != null && !uuid.isBlank()) count = questionRepository.countByQuestionId(uuid);
		if(count > 0) exists = true;
		return exists;
	}
}
