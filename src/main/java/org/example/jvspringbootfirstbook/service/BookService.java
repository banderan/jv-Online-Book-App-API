package org.example.jvspringbootfirstbook.service;

import java.util.List;
import org.example.jvspringbootfirstbook.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
