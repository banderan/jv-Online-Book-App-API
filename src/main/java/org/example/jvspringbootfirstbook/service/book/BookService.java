package org.example.jvspringbootfirstbook.service.book;

import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.book.BookSearchParametersDto;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDtoWithoutCategoryIds save(CreateBookRequestDto createBookRequestDto);

    List<BookDtoWithoutCategoryIds> findAll(Pageable pageable);

    BookDtoWithoutCategoryIds findById(Long id);

    void deleteById(Long id);

    BookDtoWithoutCategoryIds update(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDtoWithoutCategoryIds> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable);

    List<BookDtoWithoutCategoryIds> findByCategoryId(Long id);
}
