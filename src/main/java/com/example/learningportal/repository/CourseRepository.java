package com.example.learningportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learningportal.entities.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

}