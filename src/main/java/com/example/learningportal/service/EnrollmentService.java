package com.example.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.learningportal.dto.EnrollmentDto;
import com.example.learningportal.entities.EnrollmentEntity;
import com.example.learningportal.mappers.EnrollmentMapper;
import com.example.learningportal.repository.EnrollmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnrollmentService {
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private EnrollmentMapper enrollmentMapper;

	public EnrollmentDto saveEnrollment(EnrollmentDto dto) {
		EnrollmentEntity entity = enrollmentMapper.toEntity(dto);
		enrollmentRepository.save(entity);
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		return enrollmentMapper.toDto(entity);

	}

	public EnrollmentDto getEnrollmentById(Long enrollmentId) {
		Optional<EnrollmentEntity> EnrollmentOptional = enrollmentRepository.findById(enrollmentId);

		if (EnrollmentOptional.isPresent()) {
			EnrollmentEntity course = EnrollmentOptional.get();
			return enrollmentMapper.toDto(course);
		} else {

			log.error("Enrollment with ID " + enrollmentId + " not found");
			return null;
		}
	}

	public List<EnrollmentDto> getAllEnrollments() {
		List<EnrollmentEntity> entitylist = enrollmentRepository.findAll();
		return enrollmentMapper.toDto(entitylist);
	}

	public void deleteEnrollment(Long id) {
		enrollmentRepository.deleteById(id);
	}
}