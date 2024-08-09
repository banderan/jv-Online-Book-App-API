package org.example.jvspringbootfirstbook.service.category;


import org.example.jvspringbootfirstbook.dto.category.CategoryDto;
import org.example.jvspringbootfirstbook.dto.category.CreateCategoryRequestDto;
import org.example.jvspringbootfirstbook.mapper.CategoryMapper;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("""
            Verify findAll method with items in DB
            """)
    public void findALL_withItemsInDB_listOfCategoryDto() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify findAll method without items in DB
            """)
    public void findALL_withoutItemsInDB_emptyList() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify getById method with correct id should return
            """)
    public void getById_withCorrectId_returnsCategoryDto() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify getById method without id should throw EntityNotFoundException
            """)
    public void getById_withoutCorrectId_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify save method with correct id should return CategoryDto
            """)
    public void save_correctInput_ReturnsCategoryDto() {
        //Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "Category", "ok category"
        );
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());
        category.setDeleted(false);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        //When
        CategoryDto actual = categoryService.save(requestDto);
        //Then
        CategoryDto expected = categoryDto;
        Assertions.assertEquals(expected, actual);

        verify(categoryMapper, times(1)).toEntity(requestDto);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("""
            Verify save method without correct id should throw EntityNotFoundException
            """)
    public void save_emptyInput_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify update method with correct id should return CategoryDto
            """)
    public void update_withCorrectId_returnsCategoryDto() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify update method without correct id should throw EntityNotFoundException
            """)
    public void update_withoutCorrectId_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify delete method with correct id should delete Category
             from DB
            """)
    public void deleteById_withCorrectId_deleteCategoryFromDB() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify deleteById without correct id should not delete any category
            from DB
            """)
    public void deleteById_withoutCorrectId_DBWithoutChanges() {
        //Given
        //When
        //Then
    }
}
