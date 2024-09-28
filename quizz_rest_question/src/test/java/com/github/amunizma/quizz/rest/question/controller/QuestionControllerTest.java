package com.github.amunizma.quizz.rest.question.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amunizma.quizz.rest.question.controller.QuestionController;
import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.service.QuestionService;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }
    
    @Test
    public void completQuestionBaseDTO_createQuestion_Created() throws Exception {
    	QuestionBaseDTO questionBaseDTO = new QuestionBaseDTO().builder()
    			.level("A")
    			.difficulty("1")
    			.questions(Arrays.asList("el mejor amigo del hombre"))
    			.correctAnswers(Arrays.asList("El perro"))
    			.wrongAnswers(Arrays.asList("El gato","el koala","el caballo"))
    			.build();
    	
    	QuestionDTO questionDTO = new QuestionDTO().builder()
    			.level("A")
    			.difficulty("1")
    			.questions(Arrays.asList("el mejor amigo del hombre"))
    			.correctAnswers(Arrays.asList("El perro"))
    			.wrongAnswers(Arrays.asList("El gato","el koala","el caballo"))
    			.questionId("5846002f-b929-4714-aa64-27f0db3508bd")
    			.used(null)
    			.build();
        
        when(questionService.createQuestion(any(QuestionBaseDTO.class))).thenReturn(questionDTO);

        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionBaseDTO)))
                .andExpect(status().isCreated());
    }
    
    
}
