package com.github.amunizma.quizz.rest.game.service;

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

import com.github.amunizma.quizz.rest.game.dto.GameDTO;
import com.github.amunizma.quizz.rest.game.dto.QuestionGameDTO;
import com.github.amunizma.quizz.rest.game.entity.GameEntity;
import com.github.amunizma.quizz.rest.game.exception.BadRequestException;
import com.github.amunizma.quizz.rest.game.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.game.exception.NotFoundException;
import com.github.amunizma.quizz.rest.game.exception.ServiceUnavailableException;
import com.github.amunizma.quizz.rest.game.mapper.GameMapper;
import com.github.amunizma.quizz.rest.game.respository.GameRepository;
import com.mongodb.MongoException;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private ObtainIdService obtainIdService;

    @InjectMocks
    private GameServiceImpl gameService;

    private GameDTO gameDTO;
    private GameEntity gameEntity;
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
        
        gameEntity = GameEntity.builder()
        		.gameId(gameId)
        		.level(level)
        		.questionsId(null)
        		.build();
    }
    
    /** createGame() */
    @Test
    void completGameDTO_createGame_success() throws Exception {

        when(gameMapper.toGameEntity(any(GameDTO.class))).thenReturn(gameEntity);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false);
        when(gameRepository.save(any(GameEntity.class))).thenReturn(gameEntity);
        when(gameMapper.toGameDTO(any(GameEntity.class))).thenReturn(gameDTO);

    	
        GameDTO createdGame = gameService.createGame(gameDTO);
        
        verify(gameRepository, times(1)).save(any(GameEntity.class));
        assertEquals(gameDTO, createdGame);        
        
    }
    
    @Test
    void ccompletGameDTO_createGame_BadRequestException() throws Exception {

        when(gameMapper.toGameEntity(any(GameDTO.class))).thenReturn(gameEntity);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false);

        doThrow(new DataIntegrityViolationException("Data Integrity Violation Exception")).when(gameRepository).save(any(GameEntity.class));
    	
        assertThrows(BadRequestException.class, () -> gameService.createGame(gameDTO));
    }
    
    @Test
    void completGameDTO_createGame_InternalServerErrorException() throws Exception {

    	when(gameMapper.toGameEntity(any(GameDTO.class))).thenReturn(gameEntity);
        when(obtainIdService.existUUID(any(String.class))).thenReturn(false);
        
        doThrow(new MongoException("Data Integrity Violation Exception")).when(gameRepository).save(any(GameEntity.class));
    	
        assertThrows(InternalServerErrorException.class, () -> gameService.createGame(gameDTO));
    }
    
/** getGame(String id) */
    
    @Test
    void completId_getGame_success() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameMapper.toGameDTO(gameEntity)).thenReturn(gameDTO);

        GameDTO result = gameService.getGame(gameId);

        assertNotNull(result);
        assertEquals(gameDTO, result);

        // Verifica que los métodos del repositorio y el mapper fueron llamados
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameMapper, times(1)).toGameDTO(gameEntity);
    }
    
    @Test
    void completId_getGame_notFound() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción NotFoundException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
        	gameService.getGame(gameId);
        });

        assertEquals("game with gameId not found", exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameMapper, times(0)).toGameDTO(any(GameEntity.class));
    }
   
    @Test
    void completId_getGame_serviceUnavailableException() {
        
    	when(gameRepository.findById(gameId)).thenThrow(new DataAccessException("DB access error") {});

        ServiceUnavailableException exception = assertThrows(ServiceUnavailableException.class, () -> {
        	gameService.getGame(gameId);
        });

        //assertEquals("game with gameId not found", exception.getMessage());
        assertNull(exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameMapper, times(0)).toGameDTO(any(GameEntity.class));
    }
    
    @Test
    void completId_getGame_internalServerErrorException() {
        
    	when(gameRepository.findById(gameId)).thenThrow(new MongoException("Mongo Exception error") {});

        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
        	gameService.getGame(gameId);
        });

        //assertEquals("game with gameId not found", exception.getMessage());
        assertNull(exception.getMessage());

        // Verifica que el método findById fue llamado pero no el mapper
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameMapper, times(0)).toGameDTO(any(GameEntity.class));
    }
}
