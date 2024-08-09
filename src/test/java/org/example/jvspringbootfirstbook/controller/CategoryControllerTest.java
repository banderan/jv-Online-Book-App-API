package org.example.jvspringbootfirstbook.controller;


import org.example.jvspringbootfirstbook.service.book.BookService;
import org.example.jvspringbootfirstbook.service.category.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private BookService bookService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("test method")
    public void name() {
        //Given
        //When
        //Then
    }
}
