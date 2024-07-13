package org.example.jvspringbootfirstbook.service;

import org.example.jvspringbootfirstbook.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
