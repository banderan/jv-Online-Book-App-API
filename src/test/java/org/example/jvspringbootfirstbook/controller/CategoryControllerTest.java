package org.example.jvspringbootfirstbook.controller;

import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.category.CategoryDto;
import org.example.jvspringbootfirstbook.dto.category.CreateCategoryRequestDto;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.service.book.BookService;
import org.example.jvspringbootfirstbook.service.category.CategoryService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    public static final String NAME = "name";
    public static final String DESCRIPTION = "ok description";

    @Mock
    private CategoryService categoryService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("Create Category - Success")
    public void createCategory_Success() {
        // Given
        CreateCategoryRequestDto requestDto = getCreateCategoryRequestDto();
        Category category = getCategory();
        CategoryDto expectedCategory = getCategoryDtoFromCategory(category);

        when(categoryService.save(any(CreateCategoryRequestDto.class))).thenReturn(expectedCategory);

        // When
        CategoryDto actualCategory = categoryController.createCategory(requestDto);

        // Then
        assertNotNull(actualCategory);
        assertEquals(expectedCategory.getId(), actualCategory.getId());
        assertEquals(expectedCategory.getName(), actualCategory.getName());
        assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
    }

    @Test
    @DisplayName("Get All Categories - Success")
    public void getAllCategories_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<CategoryDto> expectedCategories = List.of(
                getCategoryDtoFromCategory(getCategory()),
                getCategoryDtoFromCategory(getCategory())
        );

        when(categoryService.findAll(eq(pageable))).thenReturn(expectedCategories);

        // When
        List<CategoryDto> actualCategories = categoryController.getAll(pageable);

        // Then
        assertNotNull(actualCategories);
        assertEquals(expectedCategories.size(), actualCategories.size());
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    @DisplayName("Get Category By ID - Success")
    public void getCategoryById_Success() {
        // Given
        Long categoryId = 1L;
        CategoryDto expectedCategory = getCategoryDtoFromCategory(getCategory());

        when(categoryService.getById(eq(categoryId))).thenReturn(expectedCategory);

        // When
        CategoryDto actualCategory = categoryController.getCategoryById(categoryId);

        // Then
        assertNotNull(actualCategory);
        assertEquals(expectedCategory.getId(), actualCategory.getId());
        assertEquals(expectedCategory.getName(), actualCategory.getName());
    }

    @Test
    @DisplayName("Update Category - Success")
    public void updateCategory_Success() {
        // Given
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = getCreateCategoryRequestDto();
        CategoryDto expectedCategory = getCategoryDtoFromCategory(getCategory());

        when(categoryService.update(eq(categoryId), any(CreateCategoryRequestDto.class))).thenReturn(expectedCategory);

        // When
        CategoryDto actualCategory = categoryController.updateCategory(categoryId, requestDto);

        // Then
        assertNotNull(actualCategory);
        assertEquals(expectedCategory.getId(), actualCategory.getId());
        assertEquals(expectedCategory.getName(), actualCategory.getName());
        assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
    }

    @Test
    @DisplayName("Delete Category - Success")
    public void deleteCategory_Success() {
        // Given
        Long categoryId = 1L;
        Mockito.doNothing().when(categoryService).deleteById(eq(categoryId));

        // When
        categoryController.deleteCategory(categoryId);

        // Then
        Mockito.verify(categoryService).deleteById(eq(categoryId));
    }

    @Test
    @DisplayName("Get Books By Category ID - Success")
    public void getBooksByCategoryId_Success() {
        // Given
        Long categoryId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        List<BookDtoWithoutCategoryIds> expectedBooks = List.of(
                new BookDtoWithoutCategoryIds(1L, "Book 1", "Author 1", "ISBN123",
                        BigDecimal.valueOf(9.99), "Description 1", "cover1.jpg"),
                new BookDtoWithoutCategoryIds(2L, "Book 2", "Author 2", "ISBN456",
                        BigDecimal.valueOf(19.99), "Description 2", "cover2.jpg")
        );

        when(bookService.findByCategoryId(eq(categoryId), eq(pageable))).thenReturn(expectedBooks);

        // When
        List<BookDtoWithoutCategoryIds> actualBooks = categoryController.getBooksByCategoryId(categoryId, pageable);

        // Then
        assertNotNull(actualBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());
        assertEquals(expectedBooks, actualBooks);
    }


    private static @NotNull Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName(NAME);
        category.setDescription(DESCRIPTION);
        category.setDeleted(false);
        return category;
    }

    private static @NotNull CreateCategoryRequestDto getCreateCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                NAME, DESCRIPTION
        );
        return requestDto;
    }

    private static @NotNull CategoryDto getCategoryDtoFromCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}
