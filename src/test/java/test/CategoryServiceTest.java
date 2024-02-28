package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.learningportal.dto.CategoryDto;
import com.example.learningportal.entities.CategoryEntity;
import com.example.learningportal.mappers.CategoryMapper;
import com.example.learningportal.repository.CategoryRepository;
import com.example.learningportal.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private CategoryService categoryService;

	private CategoryDto categoryDto;
	private CategoryEntity categoryEntity;

	@BeforeEach
	public void setUp() {
		categoryDto = new CategoryDto();
		categoryDto.setCategoryId(1L);
		categoryDto.setName("TestCategory");

		categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryId(1L);
		categoryEntity.setName("TestCategory");
	}

	@Test
	void testSaveCategory() {
		when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
		when(categoryRepository.save(any())).thenReturn(categoryEntity);
		when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

		CategoryDto savedCategory = categoryService.saveCategory(categoryDto);

		assertNotNull(savedCategory);
		assertEquals(categoryDto, savedCategory);
	}

	@Test
	void testGetCategoryById() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
		when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

		CategoryDto retrievedCategory = categoryService.getCategoryById(1L);

		assertNotNull(retrievedCategory);
		assertEquals(categoryDto, retrievedCategory);
	}

	@Test
	void testGetAllCategories() {
		List<CategoryEntity> categoryEntities = Arrays.asList(categoryEntity);
		when(categoryRepository.findAll()).thenReturn(categoryEntities);
		when(categoryMapper.toDto(categoryEntities)).thenReturn(Arrays.asList(categoryDto));

		List<CategoryDto> allCategories = categoryService.getAllCategories();

		assertNotNull(allCategories);
		assertFalse(allCategories.isEmpty());

	}

	@Test
	void testDeleteCategory() {
		assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
		verify(categoryRepository).deleteById(1L);
	}

	@Test
	void testUpdateCategory() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
		when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
		when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
		when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, 1L);

		assertNotNull(updatedCategory);
		assertEquals(categoryDto, updatedCategory);
	}

	@Test
	void testUpdateCategory_CategoryNotFound() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> categoryService.updateCategory(categoryDto, 1L));
	}
}
