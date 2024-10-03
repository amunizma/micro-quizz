package com.github.amunizma.quizz.rest.game.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.amunizma.quizz.rest.game.dto.GameDTO;
import com.github.amunizma.quizz.rest.game.entity.GameEntity;


@Mapper
public interface GameMapper {
	GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(target = "gameId", expression = "java(java.util.UUID.randomUUID().toString())")
    GameEntity toGameEntity(GameDTO gameDTO);

    GameDTO toGameDTO(GameEntity gameEntity);

}
