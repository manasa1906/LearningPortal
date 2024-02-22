package com.example.learningportal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.learningportal.dto.CategoryDto;
import com.example.learningportal.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, CategoryEntity> {
	CategoryDto toDto(CategoryEntity entity);

	CategoryEntity toEntity(CategoryDto dto);

	List<CategoryDto> toDto(List<CategoryEntity> entitylist);

	List<CategoryEntity> toEntity(List<CategoryDto> dtolist);
}