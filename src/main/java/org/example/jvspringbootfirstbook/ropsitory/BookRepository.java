package org.example.jvspringbootfirstbook.ropsitory;

import org.example.jvspringbootfirstbook.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
