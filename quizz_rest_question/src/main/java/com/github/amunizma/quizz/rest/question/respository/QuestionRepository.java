package com.github.amunizma.quizz.rest.question.respository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.github.amunizma.quizz.rest.question.entity.QuestionEntity;

public interface QuestionRepository extends MongoRepository<QuestionEntity, String> {
		
	@Query(value = "{ questionId: ?0 }", count = true)
    long countByQuestionId(String questionId);
	
	@Query(value = "{ level: ?0 }")
	List<QuestionEntity> findByLevel(String level);
	
	@Query(value = "{ level: ?0 }, difficulty: ?1")
	List<QuestionEntity> findByLevelAndDifficultyOrderByUsed(String level, String difficulty);

}
	
