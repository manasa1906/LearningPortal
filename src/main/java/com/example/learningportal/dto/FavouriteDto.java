package com.example.learningportal.dto;

import java.time.LocalDateTime;

import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.UserEntity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDto {
	@Id
	private long favouriteId;
	private UserEntity userId;
	private CourseEntity courseId;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
}
