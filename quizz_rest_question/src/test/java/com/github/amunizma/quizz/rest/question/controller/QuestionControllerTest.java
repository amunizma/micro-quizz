package com.github.amunizma.quizz.rest.question.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.exception.BadRequestException;
import com.github.amunizma.quizz.rest.question.exception.ConflictException;
import com.github.amunizma.quizz.rest.question.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.question.exception.NotFoundException;
import com.github.amunizma.quizz.rest.question.exception.ServiceUnavailableException;
import com.github.amunizma.quizz.rest.question.service.QuestionService;
import com.mongodb.MongoException;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    
    private QuestionBaseDTO questionBaseDTO;
    
    private QuestionDTO questionDTO ;
    
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
    }
    
    /** CREATE QUESTION */
    
    @Test
    void completQuestionBaseDTO_createQuestion_created() throws Exception {

        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void completQuestionBaseDTO_createQuestion_conflictException() throws Exception {
    	
    	doThrow(new ConflictException("Conflict Exception"))
        	.when(questionService)
        	.createQuestion(any(QuestionBaseDTO.class)); 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isConflict());
    }
    
    @Test
    void completQuestionBaseDTO_createQuestion_badRequestException() throws Exception {

    	doThrow(new BadRequestException("Bad Request Exception"))
        	.when(questionService)
        	.createQuestion(any(QuestionBaseDTO.class)); 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void completQuestionBaseDTO_createQuestion_internalServerErrorException() throws Exception {

    	doThrow(new InternalServerErrorException("Internal Server Error Exception"))
        	.when(questionService)
        	.createQuestion(any(QuestionBaseDTO.class)); 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isInternalServerError());
    }
    
    
    @ParameterizedTest
    @CsvSource({
        "null", 
        "''", //Representa una cadena vacía
        "'    '"
    })
    void incompletQuestionBaseDTOLevel_createQuestion_badRequestException(String level) throws Exception {
    	if ("null".equals(level)) {
    		level = null;
        }
    	questionBaseDTO.setLevel(level);
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(Constant.LEVEL_BAD_REQUEST));
    }
    
    @ParameterizedTest
    @CsvSource({
        "null", 
        "''", //Representa una cadena vacía
        "'    '"
    })
    void incompletQuestionBaseDTODifficulty_createQuestion_badRequestException(String difficulty) throws Exception {
    	if ("null".equals(difficulty)) {
    		difficulty = null;
        }
    	questionBaseDTO.setDifficulty(difficulty);
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(Constant.DIFFICULTY_BAD_REQUEST));
    }
    
    @ParameterizedTest
    @MethodSource("listParameterized")
    void incompletQuestionBaseDTOQuestions_createQuestion_badRequestException(List<String> questions) throws Exception {
    	questionBaseDTO.setQuestions(questions);
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(Constant.QUESTION_BAD_REQUEST));
    }
    
    @ParameterizedTest
    @MethodSource("listParameterized")
    void incompletQuestionBaseDTOCorrectAnswers_createQuestion_badRequestException(List<String> correctAnswers) throws Exception {
    	questionBaseDTO.setCorrectAnswers(correctAnswers);
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(Constant.CORRECT_ANSWER_BAD_REQUEST));
    }
    
    @ParameterizedTest
    @MethodSource("listParameterized")
    void incompletQuestionBaseDTOWrongAnswers_createQuestion_badRequestException(List<String> wrongAnswers) throws Exception {
    	questionBaseDTO.setCorrectAnswers(wrongAnswers);
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);
 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(Constant.WRONG_ANSWER_BAD_REQUEST));
    }
    
    static Collection<List<String>> listParameterized() {
        return Arrays.asList(
            Arrays.asList(),
            null
        );
    }
    
    /** GET QUESTION */
    
    @Test
    void completId_getQuestion_ok() throws Exception {
    	
    	when(questionService.getQuestion(anyString())).thenReturn(questionDTO);

        mockMvc.perform(get("/question/{id}", idQuestion))
        		.andExpect(status().isOk());
    }
    
    @Test
    void completId_getQuestion_notFound() throws Exception {
    	 when(questionService.getQuestion(anyString())).thenThrow(new NotFoundException("Question not found"));

    	 mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isNotFound());
    }
    
    
    @Test
    void completId_getQuestion_serviceUnavailableException() throws Exception {
    	 when(questionService.getQuestion(anyString())).thenThrow(new ServiceUnavailableException("Service Unavailable Exception"));

    	 mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isConflict());
    }
    
    @Test
    void completId_getQuestion_internalServerErrorExceptionn() throws Exception {
    	 when(questionService.getQuestion(anyString())).thenThrow(new InternalServerErrorException("Internal Server Error"));

    	 mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isInternalServerError());
    }
    
    @Test
    void nullId_getQuestion_invokeMethod() throws Exception {
    	idQuestion = null;

    	mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isNotFound());
    }
    
    @Test
    void emptyId_getQuestion_invokeMethod() throws Exception {
    	idQuestion = "";

    	mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isNotFound());
    }
    
    @Test
    void blankId_getQuestion_badRequest() throws Exception {
    	idQuestion = " ";

    	mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isBadRequest());
    }
    
    @Test
    void invalidId_getQuestion_badRequest() throws Exception {
    	idQuestion = "ll";

    	mockMvc.perform(get("/question/{id}", idQuestion)
    			 .contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isBadRequest());
    }


    
}
