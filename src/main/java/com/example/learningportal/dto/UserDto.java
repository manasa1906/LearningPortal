package com.example.learningportal.dto;

import java.time.LocalDateTime;

import com.example.learningportal.entities.UserRole;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	@Id
	private long userId;
	private String username;
	private String password;
	private UserRole role;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
}