package org.example.jvspringbootfirstbook.ropsitory;

import org.example.jvspringbootfirstbook.model.Book;

import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(String id);

    Optional<Book> findByAuthor(String author);

    Optional<Book> findByTitle(String title);
}
