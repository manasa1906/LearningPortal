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
@Table(name = "Courses")
public class CourseEntity {
	@Id
	@Column(name = "Course_Id")
	private long courseId;
	@Column(name = "Course_Title")
	private String title;
	@Column(name = "Course_Description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userId;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryId;
	/*
		@OneToMany(mappedBy = "course")
		private List<EnrollmentEntity> enrollments;
	
		@OneToMany(mappedBy = "course")
		private List<FavouriteEntity> favourites;*/
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