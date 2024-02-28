package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.learningportal.dto.EnrollmentDto;
import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.EnrollmentEntity;
import com.example.learningportal.entities.UserEntity;
import com.example.learningportal.mappers.EnrollmentMapper;
import com.example.learningportal.repository.EnrollmentRepository;
import com.example.learningportal.service.EnrollmentService;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

	@Mock
	private EnrollmentRepository enrollmentRepository;

	@Mock
	private EnrollmentMapper enrollmentMapper;

	@InjectMocks
	private EnrollmentService enrollmentService;

	private EnrollmentEntity enrollmentEntity;
	private EnrollmentDto enrollmentDto;
	private UserEntity userEntity;
	private CourseEntity courseEntity;

	@BeforeEach
	void setUp() {
		userEntity = new UserEntity();
		userEntity.setUserId(1L);
		// Set other properties of userEntity

		courseEntity = new CourseEntity();
		courseEntity.setCourseId(1L);
		// Set other properties of courseEntity

		enrollmentEntity = new EnrollmentEntity();
		enrollmentEntity.setEnrollmentId(1L);
		enrollmentEntity.setUserId(userEntity);
		enrollmentEntity.setCourseId(courseEntity);
		// Set other properties of enrollmentEntity

		enrollmentDto = new EnrollmentDto();
		enrollmentDto.setEnrollmentId(1L);
		enrollmentDto.setUserId(userEntity);
		enrollmentDto.setCourseId(courseEntity);
		// Set other properties of enrollmentDto

		// Mock behavior of mapper

	}

	@Test
	void testSaveEnrollment() {
		when(enrollmentMapper.toEntity(enrollmentDto)).thenReturn(enrollmentEntity);
		when(enrollmentRepository.save(any())).thenReturn(enrollmentEntity);
		when(enrollmentMapper.toDto(enrollmentEntity)).thenReturn(enrollmentDto);
		EnrollmentDto savedEnrollment = enrollmentService.saveEnrollment(enrollmentDto);

		assertNotNull(savedEnrollment);
		assertEquals(enrollmentDto, savedEnrollment);
	}

	@Test
	void testGetEnrollmentById_EnrollmentExists() {
		when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollmentEntity));
		when(enrollmentMapper.toDto(enrollmentEntity)).thenReturn(enrollmentDto);
		EnrollmentDto foundEnrollment = enrollmentService.getEnrollmentById(1L);

		assertNotNull(foundEnrollment);
		assertEquals(enrollmentDto, foundEnrollment);
	}

	@Test
	void testGetEnrollmentById_EnrollmentNotExists() {
		when(enrollmentRepository.findById(2L)).thenReturn(Optional.empty());

		EnrollmentDto foundEnrollment = enrollmentService.getEnrollmentById(2L);

		assertNull(foundEnrollment);
	}

	@Test
	void testGetAllEnrollments() {
		List<EnrollmentEntity> enrollmentEntities = new ArrayList<>();
		enrollmentEntities.add(enrollmentEntity);
		when(enrollmentRepository.findAll()).thenReturn(enrollmentEntities);

		List<EnrollmentDto> allEnrollments = enrollmentService.getAllEnrollments();

		assertNotNull(allEnrollments);

	}

	@Test
	void testDeleteEnrollment() {
		assertDoesNotThrow(() -> enrollmentService.deleteEnrollment(1L));
		verify(enrollmentRepository).deleteById(1L);
	}
}
