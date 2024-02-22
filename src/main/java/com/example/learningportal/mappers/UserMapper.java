package com.example.learningportal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.learningportal.dto.UserDto;
import com.example.learningportal.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {
	UserDto toDto(UserEntity entity);

	UserEntity toEntity(UserDto dto);

	List<UserDto> toDto(List<UserEntity> entitylist);

	List<UserEntity> toEntity(List<UserDto> dtolist);

}
