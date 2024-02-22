package com.example.learningportal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.learningportal.dto.FavouriteDto;
import com.example.learningportal.entities.FavouriteEntity;

@Mapper(componentModel = "spring")
public interface FavouriteMapper extends EntityMapper<FavouriteDto, FavouriteEntity> {
	FavouriteDto toDto(FavouriteEntity entity);

	FavouriteEntity toEntity(FavouriteDto dto);

	List<FavouriteDto> toDto(List<FavouriteEntity> entitylist);

	List<FavouriteEntity> toEntity(List<FavouriteDto> dtolist);
}