package org.example.jvspringbootfirstbook.service.book;

import org.example.jvspringbootfirstbook.dto.book.BookDto;
import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.mapper.BooksMapper;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.repository.book.BookRepository;
import org.example.jvspringbootfirstbook.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    public static final String TITLE = "Title";
    public static final String AUTHOR = "author";
    public static final String ISBN = "1231231";
    public static final BigDecimal PRICE = BigDecimal.valueOf(123.12);
    public static final String DESCRIPTION = "ok book";
    public static final String COVER_IMAGE = "image";
    public static final Set<Long> CATEGORIES_ID = Set.of();
    public static final Set<Category> CATEGORIES = Set.of();
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BooksMapper bookMapping;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("""
            Verify output for correct input in save method
            """)
    public void save_correctInput_ReturnsBookDtoWithoutCategoryIds() {
        //Given
        Long categoryId = 1L;
        Set<Long> categoriesId = new HashSet<>();
        categoriesId.add(categoryId);

        Category category = new Category();
        category.setId(categoryId);
        category.setName("category");
        category.setDescription(DESCRIPTION);
        category.setDeleted(false);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN,
                PRICE, DESCRIPTION,
                COVER_IMAGE, categoriesId
        );
        Book book = new Book();
        book.setId(1L);
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setDescription(requestDto.description());
        book.setCoverImage(requestDto.coverImage());
        book.setCategories(categories);

        BookDtoWithoutCategoryIds withoutCategoryIds = new BookDtoWithoutCategoryIds(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(), book.getDescription(),
                book.getCoverImage()
        );

        when(bookMapping.toEntity(requestDto)).thenReturn(book);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapping.toDtoWithoutCategoryIds(book)).thenReturn(
                withoutCategoryIds
        );
        //When
        BookDtoWithoutCategoryIds actual = bookService.save(requestDto);
        //Then
        BookDtoWithoutCategoryIds expected = withoutCategoryIds;
        Assertions.assertEquals(expected, actual);

        verify(bookMapping, times(1)).toEntity(requestDto);
        verify(bookRepository, times(1)).save(book);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(bookMapping, times(1)).toDtoWithoutCategoryIds(book);
        verifyNoMoreInteractions(bookMapping, bookRepository, categoryRepository);
    }

    @Test
    @DisplayName("""
            Verify output for incorrect input in save method
            """)
    public void save_emptyInput_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify findAll verify pageable
            """)
    public void findALL_verifyPageable_listOfBookDto() {
        //Given
        Book book = new Book();
        book.setId(1L);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setIsbn(ISBN);
        book.setPrice(PRICE);
        book.setDescription(DESCRIPTION);
        book.setCoverImage(COVER_IMAGE);
        book.setCategories(CATEGORIES);
        book.setDeleted(false);

        BookDto bookDto = new BookDto();
        bookDto.setCategoriesId(Set.of());
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setId(book.getId());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());

        Pageable pageable = PageRequest.of(0,10);
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(
                books, pageable, books.size()
        );
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapping.toDto(book)).thenReturn(bookDto);
        //When
        List<BookDto> actual = bookService.findAll(pageable);

        //Then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(bookDto);

        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapping, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapping);
    }

    @Test
    @DisplayName("""
            Verify findById method with correct id
            """)
    public void findById_withCorrectId_returnsBookDto() {
        //Given
        Long categoryId = 1L;
        Set<Long> categoriesId = new HashSet<>();
        categoriesId.add(categoryId);

        Category category = new Category();
        category.setId(categoryId);
        category.setName("category");
        category.setDescription(DESCRIPTION);
        category.setDeleted(false);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN,
                PRICE, DESCRIPTION,
                COVER_IMAGE, categoriesId
        );
        Book book = new Book();
        book.setId(1L);
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setDescription(requestDto.description());
        book.setCoverImage(requestDto.coverImage());
        book.setCategories(categories);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoriesId(categoriesId);

        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapping.toDto(book)).thenReturn(bookDto);
        //When
        BookDto actual = bookService.findById(bookId);
        //Then
        BookDto expected = bookDto;
        Assertions.assertEquals(expected, actual);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapping, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapping);
    }

    @Test
    @DisplayName("""
            Verify findById method without correct id
            """)
    public void findById_withoutCorrectId_throwException() {
        //Given
        CreateBookRequestDto requestDto = null;
        Book book = null;

        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        //When
        BookDto actual = bookService.findById(bookId);
        //Then

    }

    @Test
    @DisplayName("""
            Verify deleteById with correct id should delete book
            from DB
            """)
    public void deleteById_withCorrectId_deleteBookFromDB() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify deleteById without correct id should not delete any book
            from DB
            """)
    public void deleteById_withoutCorrectId_DBWithoutChanges() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify update with correct id should update & return book
            """)
    public void update_withCorrectId_returnsBookDto() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify update with correct id should throw a EntityNotFoundException
            """)
    public void update_withoutCorrectId_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify searchBooks without parameters should return empty list 
            """)
    public void searchBooks_withoutParameters_emptyList() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify searchBooks with parameters should return list of books 
            """)
    public void searchBooks_withParameters_returnsListOfBooks() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify findByCategoryId with incorrect id should throw an EntityNotFoundException
            """)
    public void findByCategoryId_withIncorrectCategoryId_throwException() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("""
            Verify findByCategoryId with incorrect id should return list of books
            """)
    public void findByCategoryId_withCorrectCategoryId_returnsListOfBooks() {
        //Given
        //When
        //Then
    }
}
