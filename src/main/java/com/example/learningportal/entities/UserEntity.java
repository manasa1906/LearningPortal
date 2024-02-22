package com.example.learningportal.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserEntity")
public class UserEntity {
	@Id
	@Column(name = "userId")
	private long userId;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	/*	@OneToMany(mappedBy = "user")
		private java.util.List<CourseEntity> userCourses;
	
		@OneToMany(mappedBy = "learner")
		private java.util.List<EnrollmentEntity> enrollments;
	
		@OneToMany(mappedBy = "learner")
		private java.util.List<FavouriteEntity> favourites;*/

	@CreationTimestamp
	@Column(name = "Created_On", nullable = false, updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "Updated_On")
	private LocalDateTime updatedOn;

	public void updateTimestamp() {
		this.updatedOn = LocalDateTime.now();
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}
