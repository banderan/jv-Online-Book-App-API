package org.example.jvspringbootfirstbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.BookDto;
import org.example.jvspringbootfirstbook.dto.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.mapper.BookMapper;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        Book model = bookMapper.toModel(createBookRequestDto);
        Book save = bookRepository.save(model);
        return bookMapper.toBookDto(save);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id " + id)
        );
        return bookMapper.toBookDto(book);
    }
}
