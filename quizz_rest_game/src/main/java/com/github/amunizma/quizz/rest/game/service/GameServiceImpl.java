package com.github.amunizma.quizz.rest.game.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.amunizma.quizz.rest.game.dto.GameDTO;
import com.github.amunizma.quizz.rest.game.entity.GameEntity;
import com.github.amunizma.quizz.rest.game.exception.BadRequestException;
import com.github.amunizma.quizz.rest.game.exception.InternalServerErrorException;
import com.github.amunizma.quizz.rest.game.mapper.GameMapper;
import com.github.amunizma.quizz.rest.game.respository.GameRepository;
import com.mongodb.MongoException;



@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private final GameRepository gameRepository;

	private final GameMapper gameMapper;
	
	private final ObtainIdService obtainIdService;
	
	public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper, ObtainIdService obtainIdService) {
    	this.gameRepository = gameRepository;
    	this.gameMapper = gameMapper;
    	this.obtainIdService = obtainIdService;
    }

	@Override
	public GameDTO createGame(GameDTO request) {
		GameDTO gameDTO = new GameDTO();
		try {
			GameEntity gameEntity = gameMapper.toGameEntity(request);
			boolean uuidExists = true;
			while (uuidExists) {
				uuidExists = obtainIdService.existUUID(gameEntity.getGameId());
				if(uuidExists) {
					gameEntity.setGameId(UUID.randomUUID().toString());
				}
			}
			gameRepository.save(gameEntity);	
			gameDTO = gameMapper.toGameDTO(gameEntity);
		}catch(DataIntegrityViolationException e1) {
			//400 Bad Request "Data integrity violation"
			throw new BadRequestException(null);
		}catch(MongoException e2) {
			//500 Internal Server Error "MongoDB error"
			throw new InternalServerErrorException(null);
		}
		
		return gameDTO;
	}
}
