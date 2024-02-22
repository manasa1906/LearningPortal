package com.example.learningportal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.learningportal.dto.EnrollmentDto;
import com.example.learningportal.entities.EnrollmentEntity;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper extends EntityMapper<EnrollmentDto, EnrollmentEntity> {
	EnrollmentDto toDto(EnrollmentEntity entity);

	EnrollmentEntity toEntity(EnrollmentDto dto);

	List<EnrollmentDto> toDto(List<EnrollmentEntity> entitylist);

	List<EnrollmentEntity> toEntity(List<EnrollmentDto> dtolist);
}