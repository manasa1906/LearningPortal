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

import com.example.learningportal.dto.FavouriteDto;
import com.example.learningportal.entities.CourseEntity;
import com.example.learningportal.entities.FavouriteEntity;
import com.example.learningportal.entities.UserEntity;
import com.example.learningportal.mappers.FavouriteMapper;
import com.example.learningportal.repository.FavouriteRepository;
import com.example.learningportal.service.FavouriteService;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceTest {

	@Mock
	private FavouriteRepository favouriteRepository;

	@Mock
	private FavouriteMapper favouriteMapper;

	@InjectMocks
	private FavouriteService favouriteService;

	private FavouriteEntity favouriteEntity;
	private FavouriteDto favouriteDto;
	private UserEntity userEntity;
	private CourseEntity courseEntity;

	@BeforeEach
	void setUp() {
		userEntity = new UserEntity();
		userEntity.setUserId(1L);

		courseEntity = new CourseEntity();
		courseEntity.setCourseId(1L);

		favouriteEntity = new FavouriteEntity();
		favouriteEntity.setFavouriteId(1L);
		favouriteEntity.setUserId(userEntity);
		favouriteEntity.setCourseId(courseEntity);

		favouriteDto = new FavouriteDto();
		favouriteDto.setFavouriteId(1L);
		favouriteDto.setUserId(userEntity);
		favouriteDto.setCourseId(courseEntity);

	}

	@Test
	void testSaveFavourite() {
		when(favouriteMapper.toEntity(favouriteDto)).thenReturn(favouriteEntity);
		when(favouriteRepository.save(any())).thenReturn(favouriteEntity);
		when(favouriteMapper.toDto(favouriteEntity)).thenReturn(favouriteDto);
		FavouriteDto savedFavourite = favouriteService.saveFavourite(favouriteDto);

		assertNotNull(savedFavourite);
		assertEquals(favouriteDto, savedFavourite);
	}

	@Test
	void testGetFavouriteById_FavouriteExists() {
		when(favouriteRepository.findById(1L)).thenReturn(Optional.of(favouriteEntity));
		when(favouriteMapper.toDto(favouriteEntity)).thenReturn(favouriteDto);
		FavouriteDto foundFavourite = favouriteService.getFavouriteById(1L);

		assertNotNull(foundFavourite);
		assertEquals(favouriteDto, foundFavourite);
	}

	@Test
	void testGetFavouriteById_FavouriteNotExists() {
		when(favouriteRepository.findById(2L)).thenReturn(Optional.empty());

		FavouriteDto foundFavourite = favouriteService.getFavouriteById(2L);

		assertNull(foundFavourite);
	}

	@Test
	void testGetAllFavourites() {
		List<FavouriteEntity> favouriteEntities = new ArrayList<>();
		favouriteEntities.add(favouriteEntity);
		when(favouriteRepository.findAll()).thenReturn(favouriteEntities);

		List<FavouriteDto> allFavourites = favouriteService.getAllFavourites();

		assertNotNull(allFavourites);

	}

	@Test
	void testDeleteFavourite() {
		assertDoesNotThrow(() -> favouriteService.deleteFavourite(1L));
		verify(favouriteRepository).deleteById(1L);
	}
}