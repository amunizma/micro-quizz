package com.github.amunizma.quizz.rest.question.service;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.entity.QuestionEntity;
import com.github.amunizma.quizz.rest.question.exception.BadRequestException;
import com.github.amunizma.quizz.rest.question.exception.ConflictException;
import com.github.amunizma.quizz.rest.question.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.question.mapper.QuestionMapper;
import com.github.amunizma.quizz.rest.question.respository.QuestionRepository;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;

public class QuestionServiceImpl implements QuestionService{
	
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final ObtainIdService obtainIdService;
	

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper, ObtainIdService obtainIdService) {
    	this.questionRepository = questionRepository;
    	this.questionMapper = questionMapper;
    	this.obtainIdService = obtainIdService;
    }
    
    /**
	  * Creates a new question in the database
	  * 
	  * @param request the data transfer object containing the question details
	  * @return the data transfer object containing the created question details and ID
	  * @throws ConflictException if a question with the same key already exists
	  * @throws BadRequestException if there is a data integrity violation
	  * @throws InternalServerErrorException if there is a MongoDB error
	  */

	@Override
	public QuestionDTO createQuestion(QuestionBaseDTO request) {
		QuestionDTO responseDTO = new QuestionDTO();
		try {
			responseDTO = questionMapper.toQuestionDTO(request);
			boolean uuidValid = true;
			while (uuidValid) {
				uuidValid = obtainIdService.existUUID(responseDTO.getQuestionId());
				responseDTO.setQuestionId(UUID.randomUUID().toString());
			}
			QuestionEntity entity = questionMapper.toQuestionEntity(responseDTO);
			questionRepository.save(entity);
			
		}catch(DataIntegrityViolationException e1) {
			//400 Bad Request "Data integrity violation"
			throw new BadRequestException(null);
		}catch(MongoException e2) {
			//500 Internal Server Error "MongoDB error"
			throw new InternalServerErrorException(null);
		}
		return responseDTO;
	}

	@Override
	public QuestionDTO getQuestion(String questionId) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
