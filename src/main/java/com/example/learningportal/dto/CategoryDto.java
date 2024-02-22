package com.example.learningportal.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	@Id
	private String categoryId;
	private String name;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
}