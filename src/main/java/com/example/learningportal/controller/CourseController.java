package com.example.learningportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learningportal.dto.CourseDto;
import com.example.learningportal.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/findAll")
	public List<CourseDto> findAllUser() {
		log.info("finding all users");
		return courseService.getAllCourses();
	}

	@GetMapping("/{courseId}")
	public CourseDto findById(@PathVariable("courseId") Long id) {
		log.info("finding through Id");
		return courseService.getCourseById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteCourseEntity(@PathVariable("id") Long id) {
		log.info("course is deleted");
		courseService.deleteCourse(id);
	}

	@PostMapping("/createCourse")
	public CourseDto saveCourse(@RequestBody CourseDto request) {
		log.info("course is created");
		return courseService.saveCourse(request);
	}

	@PutMapping("/update/{id}")
	public CourseDto updateUserEntity(@RequestBody CourseDto request, @PathVariable("id") Long id) {
		log.info("course is updated");
		return courseService.updateCourse(request, id);
	}
}
