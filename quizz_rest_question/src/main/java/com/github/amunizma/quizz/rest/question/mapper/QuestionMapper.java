package com.github.amunizma.quizz.rest.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.amunizma.quizz.rest.question.dto.QuestionBaseDTO;
import com.github.amunizma.quizz.rest.question.dto.QuestionDTO;
import com.github.amunizma.quizz.rest.question.entity.QuestionEntity;


@Mapper
public interface QuestionMapper {
	QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "questionId", expression = "java(java.util.UUID.randomUUID().toString())")
    QuestionDTO toQuestionDTO(QuestionBaseDTO questionBaseDTO);

    QuestionEntity toQuestionEntity(QuestionDTO questionDTO);

    QuestionDTO toQuestionDTO(QuestionEntity questionEntity);

}
