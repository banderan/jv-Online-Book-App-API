package org.example.jvspringbootfirstbook.service;

import java.util.List;
import org.example.jvspringbootfirstbook.dto.BookDto;
import org.example.jvspringbootfirstbook.dto.BookSearchParametersDto;
import org.example.jvspringbootfirstbook.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDto> searchBooks(BookSearchParametersDto searchParameters);

}
