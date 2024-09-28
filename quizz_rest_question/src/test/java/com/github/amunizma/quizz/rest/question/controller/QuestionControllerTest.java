package com.github.amunizma.quizz.rest.question.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.exception.BadRequestException;
import com.github.amunizma.quizz.rest.question.exception.ConflictException;
import com.github.amunizma.quizz.rest.question.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.question.service.QuestionService;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
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
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
        
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
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_Created() throws Exception {

        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_conflictException() throws Exception {
    	
    	doThrow(new ConflictException("Conflict Exception"))
        	.when(questionService)
        	.createQuestion(any(QuestionBaseDTO.class)); 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isConflict());
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_badRequestException() throws Exception {

    	doThrow(new BadRequestException("Bad Request Exception"))
        	.when(questionService)
        	.createQuestion(any(QuestionBaseDTO.class)); 

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_internalServerErrorException() throws Exception {

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
    public void incompletQuestionBaseDTOLevel_createQuestion_badRequestException(String level) throws Exception {
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
    public void incompletQuestionBaseDTODifficulty_createQuestion_badRequestException(String difficulty) throws Exception {
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
    public void incompletQuestionBaseDTOQuestions_createQuestion_badRequestException(List<String> questions) throws Exception {
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
    public void incompletQuestionBaseDTOCorrectAnswers_createQuestion_badRequestException(List<String> correctAnswers) throws Exception {
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
    public void incompletQuestionBaseDTOWrongAnswers_createQuestion_badRequestException(List<String> wrongAnswers) throws Exception {
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
    
}
