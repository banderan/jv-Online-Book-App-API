package org.example.jvspringbootfirstbook.service.book;

import java.util.List;
import org.example.jvspringbootfirstbook.dto.book.BookDto;
import org.example.jvspringbootfirstbook.dto.book.BookSearchParametersDto;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable);

}
