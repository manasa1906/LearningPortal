package com.example.learningportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.learningportal.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query(value = "SELECT u.username, c.course_title AS course_title, u2.username AS author, cat.category_name AS category_name, "
			+ "f.course_id AS favourite_course_id " + "FROM user_entity u "
			+ "INNER JOIN enrollment_entity e ON u.user_id = e.learner_id "
			+ "INNER JOIN courses c ON e.course_id = c.course_id "
			+ "INNER JOIN user_entity u2 ON c.user_id = u2.user_id "
			+ "INNER JOIN category_entity cat ON c.category_id = cat.category_id "
			+ "LEFT JOIN favourite_entity f ON u.user_id = f.learner_id AND c.course_id = f.course_id "
			+ "WHERE u.user_id = :userId", nativeQuery = true)
	List<Object[]> getUserDetailsWithCoursesAndFavourites(long userId);
}
