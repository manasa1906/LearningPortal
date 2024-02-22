package com.example.learningportal.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "CategoryEntity")

public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Category_Id")
	private long categoryId;
	@Column(name = "Category_Name")
	private String name;

	/*@OneToMany(mappedBy = "category")
	private List<CourseEntity> courses;*/

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