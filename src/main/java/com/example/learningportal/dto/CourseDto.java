package com.example.learningportal.dto;

import java.time.LocalDateTime;

import com.example.learningportal.entities.CategoryEntity;
import com.example.learningportal.entities.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	private long courseId;
	private String title;
	private String description;
	private UserEntity userId;
	private CategoryEntity categoryId;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
}