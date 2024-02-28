package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import com.example.learningportal.entities.CategoryEntity;
import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.UserEntity;
import com.example.learningportal.mappers.CourseMapper;
import com.example.learningportal.repository.CourseRepository;
import com.example.learningportal.service.CourseService;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private CourseMapper courseMapper;

	@InjectMocks
	private CourseService courseService;

	private CourseEntity courseEntity;
	private CourseDto courseDto;
	private UserEntity userEntity;
	private CategoryEntity categoryEntity;

	@BeforeEach
	void setUp() {
		userEntity = new UserEntity();
		userEntity.setUserId(1L);
		userEntity.setUsername("testUser");
		userEntity.setPassword("password");

		categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryId(1L);
		categoryEntity.setName("TestCategory");

		courseEntity = new CourseEntity();
		courseEntity.setCourseId(1L);
		courseEntity.setTitle("Test Course");
		courseEntity.setUserId(userEntity);
		courseEntity.setCategoryId(categoryEntity);
		courseEntity.setCreatedOn(LocalDateTime.now());
		courseEntity.setUpdatedOn(LocalDateTime.now());

		courseDto = new CourseDto();
		courseDto.setCourseId(1L);
		courseDto.setTitle("Test Course");
		courseDto.setUserId(userEntity);
		courseDto.setCategoryId(categoryEntity);

	}

	@Test
	void testSaveCourse() {
		when(courseMapper.toEntity(courseDto)).thenReturn(courseEntity);
		when(courseRepository.save(any())).thenReturn(courseEntity);
		when(courseMapper.toDto(courseEntity)).thenReturn(courseDto);
		CourseDto savedCourse = courseService.saveCourse(courseDto);

		assertNotNull(savedCourse);
		assertEquals(courseDto, savedCourse);
	}

	@Test
	void testGetCourseById_CourseExists() {

		when(courseMapper.toDto(courseEntity)).thenReturn(courseDto);
		when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));

		CourseDto foundCourse = courseService.getCourseById(1L);

		assertNotNull(foundCourse);
		assertEquals(courseDto, foundCourse);
	}

	@Test
	void testGetCourseById_CourseNotExists() {
		when(courseRepository.findById(2L)).thenReturn(Optional.empty());

		CourseDto foundCourse = courseService.getCourseById(2L);

		assertNull(foundCourse);
	}

	@Test
	void testGetAllCourses() {
		List<CourseEntity> courseEntities = new ArrayList<>();
		courseEntities.add(courseEntity);
		when(courseRepository.findAll()).thenReturn(courseEntities);

		List<CourseDto> allCourses = courseService.getAllCourses();

		assertNotNull(allCourses);
	}

	@Test
	void testUpdateCourse_CourseExists() {

		when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
		when(courseRepository.save(any())).thenReturn(courseEntity);

		CourseDto updatedCourse = courseService.updateCourse(courseDto, 1L);

		assertNotNull(updatedCourse);
		assertEquals(courseDto, updatedCourse);
	}

	@Test
	void testUpdateCourse_CourseNotExists() {
		when(courseRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(courseDto, 2L));
	}

	@Test
	void testDeleteCourse_CourseExists() {
		when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));

		assertDoesNotThrow(() -> courseService.deleteCourse(1L));
	}

	@Test
	void testDeleteCourse_CourseNotExists() {
		when(courseRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(2L));
	}
}
