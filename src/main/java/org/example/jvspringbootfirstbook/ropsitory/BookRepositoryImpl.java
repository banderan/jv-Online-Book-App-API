package org.example.jvspringbootfirstbook.ropsitory;

import org.example.jvspringbootfirstbook.model.Book;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findByAuthor(String author) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }
}
