package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.learningportal.dto.CourseDto;
import com.example.learningportal.dto.UserDto;
import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.UserEntity;
import com.example.learningportal.entities.UserRole;
import com.example.learningportal.mappers.CourseMapper;
import com.example.learningportal.mappers.UserMapper;
import com.example.learningportal.repository.CourseRepository;
import com.example.learningportal.repository.EnrollmentRepository;
import com.example.learningportal.repository.FavouriteRepository;
import com.example.learningportal.repository.UserRepository;
import com.example.learningportal.service.CourseService;
import com.example.learningportal.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private EnrollmentRepository enrollmentRepository;

	@Mock
	private FavouriteRepository favouriteRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private CourseMapper courseMapper;

	@InjectMocks
	private UserService userService;
	@InjectMocks
	private CourseService courseService;

	private UserEntity anotherUserEntity;
	private UserEntity userEntity;
	private UserDto anotherUserDto;
	private UserDto userDto;
	private CourseEntity anotherCourseEntity;
	private CourseEntity courseEntity;
	private CourseDto anotherCourseDto;
	private CourseDto courseDto;

	@BeforeEach
	public void setup() {
		userEntity = new UserEntity();
		userEntity.setUserId(1L);
		userEntity.setUsername("testUser");
		userEntity.setPassword("password");
		userEntity.setRole(UserRole.ADMIN);

		userDto = new UserDto();
		userDto.setUserId(1L);
		userDto.setUsername("testUser");
		userDto.setPassword("password");
		userDto.setRole(UserRole.ADMIN);
		anotherUserEntity = new UserEntity();
		anotherUserEntity.setUserId(2L);
		anotherUserEntity.setUsername("anotherUser");
		anotherUserEntity.setPassword("anotherPassword");
		anotherUserEntity.setRole(UserRole.AUTHOR);

		anotherUserDto = new UserDto();
		anotherUserDto.setUserId(2L);
		anotherUserDto.setUsername("anotherUser");
		anotherUserDto.setPassword("anotherPassword");
		anotherUserDto.setRole(UserRole.AUTHOR);

		courseEntity = new CourseEntity();
		courseEntity.setCourseId(1L);
		courseEntity.setTitle("Test Course");

		courseDto = new CourseDto();
		courseDto.setCourseId(1L);
		courseDto.setTitle("Test Course");
		anotherCourseEntity = new CourseEntity();
		anotherCourseEntity.setCourseId(2L);
		anotherCourseEntity.setTitle("Another Course");

		anotherCourseDto = new CourseDto();
		anotherCourseDto.setCourseId(2L);
		anotherCourseDto.setTitle("Another Course");
	}

	// Test for saveUser method
	@Test
	public void testSaveUser() {
		when(userMapper.toEntity(userDto)).thenReturn(userEntity);
		when(userRepository.save(userEntity)).thenReturn(userEntity);
		when(userMapper.toDto(userEntity)).thenReturn(userDto);

		UserDto savedUser = userService.saveUser(userDto);

		assertNotNull(savedUser);
		assertEquals(userDto, savedUser);
	}

	// Test for getUserById method
	@Test
	public void testGetUserById_UserExists() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
		when(userMapper.toDto(userEntity)).thenReturn(userDto);

		UserDto foundUser = userService.getUserById(1L);

		assertNotNull(foundUser);
		assertEquals(userDto, foundUser);
	}

	@Test
	public void testGetUserById_UserNotExists() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());

		UserDto foundUser = userService.getUserById(2L);

		assertNull(foundUser);
	}

	// Test for getAllUsers method
	@Test
	void testGetAllUsers() {
		List<UserEntity> userList = new ArrayList<>();
		userList.add(userEntity);
		when(userRepository.findAll()).thenReturn(userList);
		when(userMapper.toDto(userList)).thenReturn(List.of(userDto));

		List<UserDto> allUsers = userService.getAllUsers();

		assertNotNull(allUsers);

	}

	// Test for deleteUser method
	@Test
	void testDeleteUser_UserExists() {
		userService.deleteUser(1L);

		verify(userRepository).deleteById(1L);
	}

	@Test
	void testDeleteUser_UserNotExists() {
		doNothing().when(userRepository).deleteById(2L);

		userService.deleteUser(2L);

		verify(userRepository).deleteById(2L);
	}

	// Test for updateUser method
	@Test
	void testUpdateUser_UserExists() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
		when(userMapper.toEntity(userDto)).thenReturn(userEntity);
		when(userRepository.save(userEntity)).thenReturn(userEntity);
		when(userMapper.toDto(userEntity)).thenReturn(userDto);

		UserDto updatedUser = userService.updateUser(userDto, 1L);

		assertNotNull(updatedUser);
		assertEquals(userDto, updatedUser);
	}

	@Test
	void testUpdateUser_UserNotExists() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());

		UserDto updatedUser = userService.updateUser(userDto, 2L);

		assertNull(updatedUser);
	}

	// Test for saveUserwithAccess method
	@Test
	void testSaveUserwithAccess_AdminAccess_Success() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
		when(userMapper.toEntity(userDto)).thenReturn(userEntity);
		when(userRepository.save(userEntity)).thenReturn(userEntity);
		when(userMapper.toDto(userEntity)).thenReturn(userDto);

		UserDto savedUser = userService.saveUserwithAccess(userDto, UserRole.ADMIN, 1L, "testUser", "password");

		assertEquals(userDto, savedUser);
	}

	@Test
	void testSaveUserwithAccess_UserNotFound() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		UserDto savedUser = userService.saveUserwithAccess(userDto, UserRole.ADMIN, 1L, "testUser", "password");

		assertNull(savedUser);
	}

	@Test
	void testSaveUserwithAccess_UserNotAdmin() {
		userEntity.setRole(UserRole.LEARNER);
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		UserDto savedUser = userService.saveUserwithAccess(userDto, UserRole.ADMIN, 1L, "testUser", "password");

		assertNull(savedUser);
	}

	@Test
	void testSaveUserwithAccess_InvalidUsername() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		UserDto savedUser = userService.saveUserwithAccess(userDto, UserRole.ADMIN, 1L, "wrongUser", "password");

		assertNull(savedUser);
	}

	@Test
	void testSaveUserwithAccess_InvalidPassword() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		UserDto savedUser = userService.saveUserwithAccess(userDto, UserRole.ADMIN, 1L, "testUser", "wrongPassword");

		assertNull(savedUser);
	}

	@Test
	void testSaveCoursewithAccess_AuthorAccess_Success() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(anotherUserEntity));
		// Mock the behavior of courseMapper.toEntity() to return the courseEntity
		when(courseMapper.toEntity(courseDto)).thenReturn(courseEntity);
		// Mock the behavior of courseRepository.save() to return the courseEntity
		when(courseRepository.save(courseEntity)).thenReturn(courseEntity);
		// Mock the behavior of courseMapper.toDto() to return the courseDto
		when(courseMapper.toDto(courseEntity)).thenReturn(courseDto);

		// Attempt to save the course with correct user credentials
		CourseDto savedCourse = userService.saveCoursewithAccess(courseDto, UserRole.AUTHOR, 2L, "anotherUser",
				"anotherPassword");

		// Verify that the returned CourseDto object is equal to the expected courseDto
		assertEquals(courseDto, savedCourse);
	}

	//	@Test
	//	public void testSaveCoursewithAccess_UserNotFound() {
	//
	//		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
	//
	//		// Attempt to save the course with a non-existent user ID
	//		CourseDto savedCourse = userService.saveCoursewithAccess(courseDto, UserRole.AUTHOR, 3L, "anotherUser",
	//				"anotherPassword");
	//
	//		// Verify that the returned CourseDto object is null
	//		assertNull(savedCourse);
	//	}

	@Test
	public void testSaveCoursewithAccess_IncorrectPassword() {

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		CourseDto savedCourse = userService.saveCoursewithAccess(courseDto, UserRole.AUTHOR, 2L, "testUser",
				"IncorrectPassword");

		assertNull(savedCourse);
	}

	@Test
	void testSaveCoursewithAccess_InvalidUsername() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

		CourseDto savedCourse = userService.saveCoursewithAccess(courseDto, UserRole.AUTHOR, 2L, "WrongUser",
				"Password");

		assertNull(savedCourse);
	}

	@Test
	void testSaveCoursewithAccess_UnauthorizedUser() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
		CourseDto savedCourse = userService.saveCoursewithAccess(courseDto, UserRole.ADMIN, 1L, "testUser", "password");
		assertNull(savedCourse);
	}
}