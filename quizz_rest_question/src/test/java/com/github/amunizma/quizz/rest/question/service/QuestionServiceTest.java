package com.github.amunizma.quizz.rest.question.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.entity.QuestionEntity;
import com.github.amunizma.quizz.rest.question.exception.BadRequestException;
import com.github.amunizma.quizz.rest.question.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.question.mapper.QuestionMapper;
import com.github.amunizma.quizz.rest.question.respository.QuestionRepository;
import com.mongodb.MongoException;


@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private ObtainIdService obtainIdService;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private QuestionBaseDTO questionBaseDTO;
    private QuestionDTO questionDTO;
    private QuestionEntity questionEntity;
    
    @BeforeEach
    public void setup() {
       
		questionBaseDTO = QuestionBaseDTO.builder()
    			.level("A")
    			.difficulty("1")
    			.questions(Arrays.asList("el mejor amigo del hombre"))
    			.correctAnswers(Arrays.asList("El perro"))
    			.wrongAnswers(Arrays.asList("El gato","el koala","el caballo"))
    			.build();
        
        questionDTO = QuestionDTO.builder()
    			.level("A")
    			.difficulty("1")
    			.questions(Arrays.asList("el mejor amigo del hombre"))
    			.correctAnswers(Arrays.asList("El perro"))
    			.wrongAnswers(Arrays.asList("El gato","el koala","el caballo"))
    			.questionId("5846002f-b929-4714-aa64-27f0db3508bd")
    			.used(null)
    			.build();
        
        questionEntity = QuestionEntity.builder()
    			.level("A")
    			.difficulty("1")
    			.questions(Arrays.asList("el mejor amigo del hombre"))
    			.correctAnswers(Arrays.asList("El perro"))
    			.wrongAnswers(Arrays.asList("El gato","el koala","el caballo"))
    			.questionId("5846002f-b929-4714-aa64-27f0db3508bd")
    			.used(null)
    			.build();
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_success() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);

    	
        QuestionDTO createdQuestion = questionService.createQuestion(questionBaseDTO);
        
        verify(questionRepository, times(1)).save(any(QuestionEntity.class));
        assertEquals(questionDTO, createdQuestion);        
        
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_BadRequestException() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);
        doThrow(new DataIntegrityViolationException("Data Integrity Violation Exception")).when(questionRepository).save(any(QuestionEntity.class));
    	
        assertThrows(BadRequestException.class, () -> questionService.createQuestion(questionBaseDTO));
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_InternalServerErrorException() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);
        doThrow(new MongoException("Data Integrity Violation Exception")).when(questionRepository).save(any(QuestionEntity.class));
    	
        assertThrows(InternalServerErrorException.class, () -> questionService.createQuestion(questionBaseDTO));
    }
   
}
