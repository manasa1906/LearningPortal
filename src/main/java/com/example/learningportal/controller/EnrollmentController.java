package com.example.learningportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learningportal.dto.EnrollmentDto;
import com.example.learningportal.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/Enrollments")
public class EnrollmentController {
	@Autowired
	private EnrollmentService enrollmentService;

	@GetMapping("/findAll")
	public List<EnrollmentDto> findAllEnrollment() {
		log.info("Fetching all the Enrollments");
		return enrollmentService.getAllEnrollments();
	}

	@GetMapping("find/{enrollmentId}")
	public EnrollmentDto findById(@PathVariable("enrollmentId") Long id) {
		log.info("Fetching enrollment details");
		return enrollmentService.getEnrollmentById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteEnrollment(@PathVariable("id") Long id) {
		log.info("enrollment details are deleted");
		enrollmentService.deleteEnrollment(id);
	}

	@PostMapping("/createEnrollment")
	public EnrollmentDto saveEnrollment(@RequestBody EnrollmentDto request) {
		log.info("Enrollment is created");
		return enrollmentService.saveEnrollment(request);
	}
}