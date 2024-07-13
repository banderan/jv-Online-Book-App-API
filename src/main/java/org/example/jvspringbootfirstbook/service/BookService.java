package org.example.jvspringbootfirstbook.service;

import org.example.jvspringbootfirstbook.model.Book;

public interface BookService {
    Book save(Book book);

    Book getBookById(int id);

    Book getBookByTitle(String title);

    Book getBookByAuthor(String author);
}
