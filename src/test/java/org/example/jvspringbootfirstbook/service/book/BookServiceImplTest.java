package org.example.jvspringbootfirstbook.service.book;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.jvspringbootfirstbook.dto.book.BookDto;
import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.mapper.BooksMapper;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.repository.book.BookRepository;
import org.example.jvspringbootfirstbook.repository.category.CategoryRepository;
import org.jetbrains.annotations.NotNull;
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
    public static final Long CATEGORY_ID = 1L;
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
        Set<Long> categoriesId = new HashSet<>();
        categoriesId.add(CATEGORY_ID);

        Category category = getCategory(CATEGORY_ID);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN,
                PRICE, DESCRIPTION,
                COVER_IMAGE, categoriesId
        );
        Book book = getBook();
        book.setCategories(categories);

        BookDtoWithoutCategoryIds expected = getBookDtoWithoutCategoryIdsFromBook(book);

        when(bookMapping.toEntity(requestDto)).thenReturn(book);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapping.toDtoWithoutCategoryIds(book)).thenReturn(
                expected
        );
        //When
        BookDtoWithoutCategoryIds actual = bookService.save(requestDto);
        //Then
        Assertions.assertEquals(expected, actual);

        verify(bookMapping, times(1)).toEntity(requestDto);
        verify(bookRepository, times(1)).save(book);
        verify(categoryRepository, times(1)).findById(CATEGORY_ID);
        verify(bookMapping, times(1)).toDtoWithoutCategoryIds(book);
        verifyNoMoreInteractions(bookMapping, bookRepository, categoryRepository);
    }

    @Test
    @DisplayName("""
            Verify findAll verify pageable
            """)
    public void findAll_VerifyPageable_ReturnsList() {
        //Given
        Book book = getBook();

        BookDto bookDto = getBookDtoFromBook(book);

        Pageable pageable = PageRequest.of(0, 10);
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
        Set<Long> categoriesId = new HashSet<>();
        categoriesId.add(CATEGORY_ID);

        Category category = getCategory(CATEGORY_ID);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN,
                PRICE, DESCRIPTION,
                COVER_IMAGE, categoriesId
        );

        Book book = getBook();
        book.setCategories(categories);

        BookDto expected = getBookDtoFromBook(book);
        expected.setCategoriesId(categoriesId);

        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapping.toDto(book)).thenReturn(expected);
        //When
        BookDto actual = bookService.findById(bookId);
        //Then
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
        Long bookId = -100L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        //When
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(bookId));

        //Then
        Assertions.assertEquals("Can't find book with id: " + bookId,
                exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify deleteById with correct id should delete book
            from DB
            """)
    public void deleteById_withCorrectId_deleteBookFromDB() {
        //Given
        Book book = getBook();
        Long bookId = book.getId();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        //When
        bookService.deleteById(bookId);

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(bookId));

        //Then
        Assertions.assertEquals("Can't find book with id: " + bookId,
                exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify update with correct id should update & return book
            """)
    public void update_withCorrectId_returnsBookDto() {
        //Given
        CreateBookRequestDto createBookRequestDto = getCreateBookRequestDto();
        Book book = getBook();
        Long bookId = book.getId();

        BookDto expected = getBookDtoFromBook(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapping.toEntity(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapping.toDto(book)).thenReturn(expected);
        //When
        BookDto actual = bookService.update(bookId, createBookRequestDto);
        //Then
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapping, times(1)).toEntity(createBookRequestDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapping, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapping);
    }

    @Test
    @DisplayName("""
            Verify update with correct id should throw a EntityNotFoundException
            """)
    public void update_withoutCorrectId_throwException() {
        //Given
        CreateBookRequestDto createBookRequestDto = getCreateBookRequestDto();
        Book book = getBook();
        book.setId(-123L);
        Long bookId = book.getId();
        //When
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.update(bookId, createBookRequestDto));
        //Then
        Assertions.assertEquals("Can't find book with id: " + bookId, exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify findByCategoryId with incorrect id should throw an EntityNotFoundException
            """)
    public void findByCategoryId_withIncorrectCategoryId_throwException() {
        //Given
        Set<Long> categoriesId = new HashSet<>();
        categoriesId.add(CATEGORY_ID);

        Category category = getCategory(CATEGORY_ID);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Book book = getBook();
        book.setCategories(categories);
        Long bookId = book.getId();

        Pageable pageable = PageRequest.of(0, 10);

        List<Book> bookList = List.of(book);
        BookDtoWithoutCategoryIds bookDtoWithoutCategoryIdsFromBook =
                getBookDtoWithoutCategoryIdsFromBook(book);

        when(bookRepository.findAllByCategoriesId(CATEGORY_ID, pageable)).thenReturn(bookList);
        when(bookMapping.toDtoWithoutCategoryIds(book)).thenReturn(
                bookDtoWithoutCategoryIdsFromBook
        );
        //When
        List<BookDtoWithoutCategoryIds> byCategoryId = bookService
                .findByCategoryId(CATEGORY_ID, pageable);
        //Then
        Assertions.assertEquals(bookDtoWithoutCategoryIdsFromBook, byCategoryId.get(0));

        verify(bookRepository, times(1))
                .findAllByCategoriesId(CATEGORY_ID, pageable);
        verify(bookMapping, times(1)).toDtoWithoutCategoryIds(book);
        verifyNoMoreInteractions(bookRepository, bookMapping);
    }

    @Test
    @DisplayName("""
            Verify findByCategoryId with incorrect id should return list of books
            """)
    public void findByCategoryId_withCorrectCategoryId_returnsListOfBooks() {
        //Given
        Long categoryId = 123123123123198669L;
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> bookList = List.of();

        when(bookRepository.findAllByCategoriesId(categoryId, pageable))
                .thenReturn(bookList);
        //When
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findByCategoryId(categoryId, pageable));
        //Then
        Assertions.assertEquals("Can't find book with id: " + categoryId, exception.getMessage());
        verify(bookRepository, times(1)).findAllByCategoriesId(categoryId, pageable);
        verifyNoMoreInteractions(bookRepository);
    }

    private static @NotNull Book getBook() {
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
        return book;
    }

    private static @NotNull Category getCategory(Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName("category");
        category.setDescription(DESCRIPTION);
        category.setDeleted(false);
        return category;
    }

    private static @NotNull BookDto getBookDtoFromBook(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setCategoriesId(Set.of());
        bookDto.setTitle(book.getTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setId(book.getId());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        return bookDto;
    }

    private static @NotNull
            BookDtoWithoutCategoryIds getBookDtoWithoutCategoryIdsFromBook(Book book) {
        BookDtoWithoutCategoryIds withoutCategoryIds = new BookDtoWithoutCategoryIds(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPrice(), book.getDescription(),
                book.getCoverImage()
        );
        return withoutCategoryIds;
    }

    private static @NotNull CreateBookRequestDto getCreateBookRequestDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto(
                TITLE, AUTHOR, ISBN, PRICE, DESCRIPTION, COVER_IMAGE, CATEGORIES_ID
        );
        return createBookRequestDto;
    }
}
