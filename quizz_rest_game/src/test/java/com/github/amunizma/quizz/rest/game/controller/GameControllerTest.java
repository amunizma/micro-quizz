package com.github.amunizma.quizz.rest.game.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amunizma.quizz.rest.game.dto.GameDTO;
import com.github.amunizma.quizz.rest.game.dto.QuestionGameDTO;
import com.github.amunizma.quizz.rest.game.exception.BadRequestException;
import com.github.amunizma.quizz.rest.game.exception.ConflictException;
import com.github.amunizma.quizz.rest.game.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.game.service.GameService;

@WebMvcTest(GameController.class)
class GameControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @InjectMocks
    private GameController questionController;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private GameDTO gameDTO ;
    
    private String gameId;
    
    @BeforeEach
    void setup() {
    	
    	gameId = "5846002f-b929-4714-aa64-27f0db3508bd";
        String level = "A";
        String difficulty = "1";
        String correct = "n/s";
        
        QuestionGameDTO questionGameDTO01= QuestionGameDTO.builder()
        		.questionId("5846056f-b929-4714-aa64-27f0db3508bl")
        		.difficulty(difficulty)
        		.correct(correct)
        		.build();
        
        QuestionGameDTO questionGameDTO02= QuestionGameDTO.builder()
        		.questionId("2946056f-b914-3614-aa63-27f0db3508bl")
        		.difficulty(difficulty)
        		.correct(correct)
        		.build();
        
        List<QuestionGameDTO> questions = Arrays.asList(questionGameDTO01, questionGameDTO02);
        
        gameDTO = GameDTO.builder()
        		.level(level)
        		.questions(questions)
        		.build();
    }
    
    /** CREATE QUESTION */
    
    @Test
    void completGameDTO_createGame_created() throws Exception {

        when(gameService.createGame(any(GameDTO.class))).thenReturn(gameDTO);

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isCreated());
    }
    
    @ParameterizedTest
    @CsvSource({
        "null", 
        "''", //Representa una cadena vacía
        "'    '"
    })
    void incompletLevel_createGame_badRequest(String level) throws Exception {
    	if ("null".equals(level)) {
    		level = null;
        }
    	
    	gameDTO.setLevel(level);

    	mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
 				.andExpect(status().isBadRequest());
    }
    
    @ParameterizedTest
    @MethodSource("listParameterized")
    void incompletQuestions_createGame_badRequest(List<QuestionGameDTO> questions) throws Exception {
    	
    	gameDTO.setQuestions(questions);

    	mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
 				.andExpect(status().isBadRequest());
    }
    
    static Collection<List<String>> listParameterized() {
        return Arrays.asList(
            Arrays.asList(),
            null
        );
    }
    
    @Test
    void completGameDTO_createGame_conflictException() throws Exception {
    	
    	doThrow(new ConflictException("Conflict Exception"))
        	.when(gameService)
        	.createGame(any(GameDTO.class)); 
    	
    	mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isConflict());
    }
    
    @Test
    void completGameDTO_createGame_badRequestException() throws Exception {

    	doThrow(new BadRequestException("Bad Request Exception"))
        	.when(gameService)
        	.createGame(any(GameDTO.class)); 

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void completGameDTO_createGame_internalServerErrorException() throws Exception {

    	doThrow(new InternalServerErrorException("Internal Server Error Exception"))
        	.when(gameService)
        	.createGame(any(GameDTO.class)); 

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isInternalServerError());
    }
    
}
