package com.github.amunizma.quizz.rest.game.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.amunizma.quizz.rest.game.respository.GameRepository;


@ExtendWith(MockitoExtension.class)
class ObtainIdServiceTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ObtainIdServiceImpl obtainIdService;

    
    @BeforeEach
    void setup() {
       
    }
    
    @Test
    void completUuidFound_existUUID_exists() {
        String uuid = "existing-uuid";
        when(gameRepository.countByGameId(uuid)).thenReturn(1L);

        boolean result = obtainIdService.existUUID(uuid);
        assertTrue(result);  
        
    }
    
    @Test
    void completUuidNotFound_existUUID_notExists() {
        String uuid = "existing-uuid";
        when(gameRepository.countByGameId(uuid)).thenReturn(0L);

        boolean result = obtainIdService.existUUID(uuid);
        assertFalse(result);  
        
    }
    
    @Test
    void incompletUuidNull_existUUID_notExists() {

    	String uuid = null;

        boolean result = obtainIdService.existUUID(uuid);
        assertFalse(result);  
    }
   
}
