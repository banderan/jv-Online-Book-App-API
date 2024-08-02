package org.example.jvspringbootfirstbook.service.book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.book.BookDtoWithoutCategoryIds;
import org.example.jvspringbootfirstbook.dto.book.BookSearchParametersDto;
import org.example.jvspringbootfirstbook.dto.book.CreateBookRequestDto;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.mapper.BooksMapper;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.book.BookRepository;
import org.example.jvspringbootfirstbook.repository.book.BookSpecificationBuilder;
import org.example.jvspringbootfirstbook.repository.category.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BooksMapper bookMapping;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDtoWithoutCategoryIds save(CreateBookRequestDto createBookRequestDto) {
        Book book = bookMapping.toEntity(createBookRequestDto);
        book.setCategories(createBookRequestDto.categoriesId().stream()
                .map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet())
        );
        return bookMapping.toDtoWithoutCategoryIds(bookRepository.save(book));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapping::toDtoWithoutCategoryIds)
                .toList();
    }

    @Override
    public BookDtoWithoutCategoryIds findById(Long id) {
        return bookMapping.toDtoWithoutCategoryIds(
                bookRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't find book with id: " + id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDtoWithoutCategoryIds update(
            Long id,
            CreateBookRequestDto createBookRequestDto) {
        if (!bookRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Can't find book with id: " + id);
        }
        Book book = bookMapping.toEntity(createBookRequestDto);
        book.setId(id);
        return bookMapping.toDtoWithoutCategoryIds(bookRepository.save(book));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> searchBooks(
            BookSearchParametersDto searchParameters,
            Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream().map(bookMapping::toDtoWithoutCategoryIds).toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findByCategoryId(Long id) {
        List<Book> allByCategoriesId = bookRepository.findAllByCategoriesId(id);
        if (allByCategoriesId.isEmpty()) {
            throw new EntityNotFoundException("Can't find book with id: " + id);
        }
        return allByCategoriesId.stream()
                .map(bookMapping::toDtoWithoutCategoryIds)
                .toList();
    }
}
