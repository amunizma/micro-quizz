package com.github.amunizma.quizz.rest.game.respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.github.amunizma.quizz.rest.game.entity.GameEntity;


public interface GameRepository extends MongoRepository<GameEntity, String> {
	
	@Query(value = "{ gameId: ?0 }", count = true)
    long countByGameId(String gameId);
	
}
