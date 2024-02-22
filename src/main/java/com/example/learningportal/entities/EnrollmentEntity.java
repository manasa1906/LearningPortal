package com.example.learningportal.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "EnrollmentEntity")
public class EnrollmentEntity {
	@Id
	@Column(name = "enrollmentId")
	private long enrollmentId;

	@ManyToOne
	@JoinColumn(name = "learner_id")
	private UserEntity userId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private CourseEntity courseId;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false, nullable = false)
	private LocalDateTime createdOn;
	@UpdateTimestamp
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	public void updateTimestamp() {
		this.updatedOn = LocalDateTime.now();
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}