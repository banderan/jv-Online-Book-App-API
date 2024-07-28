package org.example.jvspringbootfirstbook.mapper;

import org.example.jvspringbootfirstbook.config.MapperConfig;
import org.example.jvspringbootfirstbook.dto.book.BookDto;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BooksMapper {
    BookDto toBookDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
