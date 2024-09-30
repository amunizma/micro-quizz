package com.github.amunizma.quizz.rest.question.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.entity.QuestionEntity;
import com.github.amunizma.quizz.rest.question.exception.BadRequestException;
import com.github.amunizma.quizz.rest.question.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.question.exception.NotFoundException;
import com.github.amunizma.quizz.rest.question.exception.ServiceUnavailableException;
import com.github.amunizma.quizz.rest.question.mapper.QuestionMapper;
import com.github.amunizma.quizz.rest.question.respository.QuestionRepository;
import com.mongodb.MongoException;


@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
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
    private String idQuestion;
    
    @BeforeEach
    void setup() {
       idQuestion = "5846002f-b929-4714-aa64-27f0db3508bd";
       String level = "A";
       String difficulty = "1";
       List<String> questions = Arrays.asList("el mejor amigo del hombre");
       List<String> correctAnswers = Arrays.asList("El perro");
       List<String> wrongAnswers = Arrays.asList("El gato","el koala","el caballo");
       
		questionBaseDTO = QuestionBaseDTO.builder()
    			.level(level)
    			.difficulty(difficulty)
    			.questions(questions)
    			.correctAnswers(correctAnswers)
    			.wrongAnswers(wrongAnswers)
    			.build();
        
        questionDTO = QuestionDTO.builder()
        		.level(level)
    			.difficulty(difficulty)
    			.questions(questions)
    			.correctAnswers(correctAnswers)
    			.wrongAnswers(wrongAnswers)
    			.questionId(idQuestion)
    			.used(null)
    			.build();
        
        questionEntity = QuestionEntity.builder()
        		.level(level)
    			.difficulty(difficulty)
    			.questions(questions)
    			.correctAnswers(correctAnswers)
    			.wrongAnswers(wrongAnswers)
    			.questionId(idQuestion)
    			.used(null)
    			.build();
        
    }
    
    /** createQuestion() */
    @Test
    void completQuestionBaseDTO_createQuestion_success() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);

    	
        QuestionDTO createdQuestion = questionService.createQuestion(questionBaseDTO);
        
        verify(questionRepository, times(1)).save(any(QuestionEntity.class));
        assertEquals(questionDTO, createdQuestion);        
        
    }
    
    @Test
    void completQuestionBaseDTO_createQuestion_BadRequestException() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);
        doThrow(new DataIntegrityViolationException("Data Integrity Violation Exception")).when(questionRepository).save(any(QuestionEntity.class));
    	
        assertThrows(BadRequestException.class, () -> questionService.createQuestion(questionBaseDTO));
    }
    
    @Test
    void completQuestionBaseDTO_createQuestion_InternalServerErrorException() throws Exception {

    	// Configurar mocks
        when(questionMapper.toQuestionDTO(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false); // Simular que el UUID no existe
        when(questionMapper.toQuestionEntity(any(QuestionDTO.class))).thenReturn(questionEntity);
        doThrow(new MongoException("Data Integrity Violation Exception")).when(questionRepository).save(any(QuestionEntity.class));
    	
        assertThrows(InternalServerErrorException.class, () -> questionService.createQuestion(questionBaseDTO));
    }
    
    /** getQuestion(String id) */
    
    @Test
    void completId_getQuestion_success() {
        when(questionRepository.findById(idQuestion)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.toQuestionDTO(questionEntity)).thenReturn(questionDTO);

        QuestionDTO result = questionService.getQuestion(idQuestion);

        assertNotNull(result);
        assertEquals(questionDTO, result);

        // Verifica que los métodos del repositorio y el mapper fueron llamados
        verify(questionRepository, times(1)).findById(idQuestion);
        verify(questionMapper, times(1)).toQuestionDTO(questionEntity);
    }
    
    @Test
    void completId_getQuestion_notFound() {
        when(questionRepository.findById(idQuestion)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción NotFoundException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
        	questionService.getQuestion(idQuestion);
        });

        assertEquals("question with questionId not found", exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(questionRepository, times(1)).findById(idQuestion);
        verify(questionMapper, times(0)).toQuestionDTO(any(QuestionEntity.class));
    }
   
    @Test
    void completId_getQuestion_serviceUnavailableException() {
        when(questionRepository.findById(idQuestion)).thenThrow(new DataAccessException("DB access error") {});

        // Verifica que se lance la excepción NotFoundException
        ServiceUnavailableException exception = assertThrows(ServiceUnavailableException.class, () -> {
        	questionService.getQuestion(idQuestion);
        });

        //assertEquals("question with questionId not found", exception.getMessage());
        assertNull(exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(questionRepository, times(1)).findById(idQuestion);
        verify(questionMapper, times(0)).toQuestionDTO(any(QuestionEntity.class));
    }
    
    @Test
    void completId_getQuestion_internalServerErrorException() {
        when(questionRepository.findById(idQuestion)).thenThrow(new MongoException("Mongo Exception error") {});

        // Verifica que se lance la excepción NotFoundException
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
        	questionService.getQuestion(idQuestion);
        });

        //assertEquals("question with questionId not found", exception.getMessage());
        assertNull(exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(questionRepository, times(1)).findById(idQuestion);
        verify(questionMapper, times(0)).toQuestionDTO(any(QuestionEntity.class));
    }
 
}
