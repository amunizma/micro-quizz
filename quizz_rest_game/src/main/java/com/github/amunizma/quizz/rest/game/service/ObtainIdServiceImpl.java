package com.github.amunizma.quizz.rest.game.service;

import com.github.amunizma.quizz.rest.game.respository.GameRepository;

public class ObtainIdServiceImpl implements ObtainIdService{
	
	private final GameRepository gameRepository;

    public ObtainIdServiceImpl(GameRepository questionRepository) {
    	this.gameRepository = questionRepository;
    }
	/**
	 * Check if the ID already exists
	 * 
	 * @param uuid ID to check
	 * @return True | False
	 */
	public boolean existUUID(String uuid) {
		boolean exists = false;
		long count = 0;
		if(uuid != null && !uuid.isBlank()) count = gameRepository.countByGameId(uuid);
		if(count > 0) exists = true;
		return exists;
	}
}
