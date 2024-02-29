package com.example.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.learningportal.dto.CourseDto;
import com.example.learningportal.dto.UserDto;
import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.EnrollmentEntity;
import com.example.learningportal.entities.FavouriteEntity;
import com.example.learningportal.entities.UserEntity;
import com.example.learningportal.entities.UserRole;
import com.example.learningportal.mappers.CourseMapper;
import com.example.learningportal.mappers.UserMapper;
import com.example.learningportal.repository.CourseRepository;
import com.example.learningportal.repository.EnrollmentRepository;
import com.example.learningportal.repository.FavouriteRepository;
import com.example.learningportal.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private FavouriteRepository favouriteRepository;

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CourseMapper courseMapper;

	public UserDto saveUser(UserDto dto) {
		UserEntity entity = userMapper.toEntity(dto);
		userRepository.save(entity);
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		return userMapper.toDto(entity);

	}

	public UserDto getUserById(Long userId) {
		// Retrieve the optional user entity from the repository
		Optional<UserEntity> userOptional = userRepository.findById(userId);

		// Check if the optional contains a value
		if (userOptional.isPresent()) {
			// If a user entity is found, retrieve it and return its DTO
			UserEntity user = userOptional.get();
			return userMapper.toDto(user);
		} else {
			// If no user entity is found with the given ID, return null or throw an exception as per your requirement
			log.error("User with ID " + userId + " not found");
			return null;
		}
	}

	public List<UserDto> getAllUsers() {
		List<UserEntity> entitylist = userRepository.findAll();
		return userMapper.toDto(entitylist);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserDto updateUser(UserDto dto, Long id) {
		Optional<UserEntity> checkExistinguser = userRepository.findById(id);
		if (!checkExistinguser.isPresent())
			log.error("User Id " + id + " Not Found!");

		UserEntity entity = userMapper.toEntity(dto);
		userRepository.save(entity);
		return userMapper.toDto(entity);
	}

	public UserDto saveUserwithAccess(UserDto userentityrequest, UserRole usertype, Long id, String username,
			String password) {
		if (usertype != UserRole.ADMIN) {
			log.error("usertype is not admin");
			return null;
		}

		Optional<UserEntity> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			log.error("ADMIN with ID " + id + " not present");
			return null;
		}

		UserEntity user = userOptional.get();
		if (user.getRole() != UserRole.ADMIN) {
			log.error("User with the ID " + id + " is not an admin user.");
			return null;
		}
		if (!user.getUsername().equals(username)) {
			log.error("user with username" + username + "not found");
			return null;
		}
		if (!user.getPassword().equals(password)) {
			log.error("Password of ADMIN is incorrect");
			return null;
		}

		// If all conditions are met, save the userEntity and return corresponding DTO
		UserEntity userEntity = userMapper.toEntity(userentityrequest);
		userEntity.setUserId(id); // Assuming you want to update the existing user
		userEntity = userRepository.save(userEntity);
		log.info("user entity saved");
		return userMapper.toDto(userEntity);
	}

	public CourseDto saveCoursewithAccess(CourseDto courserequest, UserRole usertype, Long id, String username,
			String password) {
		Boolean flag = true;
		if (usertype != UserRole.AUTHOR) {
			log.error("Only ADMIN users are allowed to perform this operation.");
			flag = false;
		}
		Optional<UserEntity> userOptional = userRepository.findById(id);
		UserEntity user = userOptional.get();
		if (user.getUserId() != (id)) {
			flag = false;
			log.error("AUTHOR with ID " + id + "is  not found.");
		}
		if (user.getRole() != UserRole.AUTHOR) {
			flag = false;
			log.error("User with user id " + id + " is not an author user.");
		}
		if (!user.getUsername().equals(username)) {
			flag = false;
			log.error("username with " + username + "of AUTHOR is not present");
		}
		if (!user.getPassword().equals(password)) {
			flag = false;
			log.error("Password of AUTHOR is incorrect");
		}
		if (Boolean.TRUE.equals(flag)) {
			CourseEntity courseEntity = courseMapper.toEntity(courserequest);
			courseRepository.save(courseEntity);
			log.info("course saved");
			return courseMapper.toDto(courseEntity);
		}
		return null;
	}

	public CourseDto updateCoursewithAccess(CourseDto courserequest, UserRole usertype, Long id, String username,
			String password, long courseid) {
		Boolean flag = false;
		if (usertype != UserRole.AUTHOR) {
			log.error("Only ADMIN users are allowed to perform this operation.");
			flag = true;
		}
		Optional<UserEntity> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty()) {
			log.error("User with ID " + id + " not found.");
			return null; // or throw an exception, depending on your requirements
		}

		UserEntity user = userOptional.get();
		if (user.getUserId() != (id)) {
			log.error("AUTHOR with ID " + id + "isn't found.");
			flag = true;
		}
		if (user.getRole().equals(UserRole.AUTHOR)) {
			log.error("User with ID of " + id + " is not an author user.");
			flag = true;
		}
		if (!user.getUsername().equals(username)) {
			log.error("username of AUTHOR is incorrect");
			flag = true;
		}
		if (!user.getPassword().equals(password)) {
			log.error("Password of AUTHOR is incorrect");
			flag = true;
		}
		Optional<CourseEntity> checkExistinguser = courseRepository.findById(courseid);
		if (!checkExistinguser.isPresent())
			log.error("Course Id " + courseid + " Not Found!");

		if (Boolean.FALSE.equals(flag)) {
			CourseEntity courseEntity = courseMapper.toEntity(courserequest);
			courseRepository.save(courseEntity);
			log.info("course updated");
			return courseMapper.toDto(courseEntity);
		} else {
			return null;
		}
	}

	public String enrollmentwithAccess(UserRole role, Long id, String username, String password, long courseid) {
		Boolean flag = false;
		if (role != UserRole.LEARNER) {
			log.error("Only LEARNER are allowed to perform this operation.");
			flag = true;
		}
		Optional<UserEntity> userOptional = userRepository.findById(id);
		UserEntity user = userOptional.get();

		if (user.getUserId() != (id)) {
			log.error("learner with ID " + id + " not found.");
			flag = true;
		}
		if (!user.getRole().equals(UserRole.LEARNER)) {
			log.error("User with ID " + id + " is not an learner .");
			flag = true;
		}
		if (!user.getUsername().equals(username)) {
			log.error("username of LEARNER is incorrect");
			flag = true;
		}
		if (!user.getPassword().equals(password)) {
			log.error("Password of LEARNER is incorrect");
			flag = true;
		}
		if (Boolean.FALSE.equals(flag)) {
			Optional<CourseEntity> userOptiona1 = courseRepository.findById(courseid);
			CourseEntity course = userOptiona1.get();
			EnrollmentEntity enrol = new EnrollmentEntity();
			enrol.setCourseId(course);
			enrol.setUserId(user);
			enrollmentRepository.save(enrol);
			return "User enrolled to the course";
		}
		return "There was an error";
	}

	public String saveFavoriteEntity(UserRole role, Long id, String username, String password, long courseid) {
		Boolean flag = false;
		if (role != UserRole.LEARNER) {
			log.error("Only LEARNER are allowed to perform this operation.");
			flag = true;
		}
		Optional<UserEntity> userOptional = userRepository.findById(id);
		UserEntity user = userOptional.get();
		Optional<CourseEntity> courseOptiona1 = courseRepository.findById(courseid);
		CourseEntity course = courseOptiona1.get();
		if (user.getUserId() != (id)) {
			log.error("learner with ID " + id + " not found.");
			flag = true;
		}
		if (!user.getRole().equals(UserRole.LEARNER)) {
			log.error("User with ID " + id + " is not an learner .");
			flag = true;
		}
		if (!user.getUsername().equals(username)) {
			log.error("username of LEARNER is incorrect");
			flag = true;
		}
		if (!user.getPassword().equals(password)) {
			log.error("Password of LEARNER is incorrect");
			flag = true;
		}
		if (Boolean.FALSE.equals(flag)) {
			FavouriteEntity fav = new FavouriteEntity();
			fav.setCourseId(course);
			fav.setUserId(user);
			favouriteRepository.save(fav);
			return "favorite added";
		} else {
			return "there was an error";
		}
	}

	public List<Object[]> getUserDetailsWithCoursesAndFavourites(long userId) {
		return userRepository.getUserDetailsWithCoursesAndFavourites(userId);
	}

}
