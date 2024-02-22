package com.example.learningportal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.learningportal.dto.CourseDto;
import com.example.learningportal.entities.CourseEntity;

@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDto, CourseEntity> {
	CourseDto toDto(CourseEntity entity);

	CourseEntity toEntity(CourseDto dto);

	List<CourseDto> toDto(List<CourseEntity> entitylist);

	List<CourseEntity> toEntity(List<CourseDto> dtolist);
}