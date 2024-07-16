package org.example.jvspringbootfirstbook.repository;

import java.util.List;
import org.example.jvspringbootfirstbook.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
