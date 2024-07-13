package org.example.jvspringbootfirstbook.ropsitory;

import org.example.jvspringbootfirstbook.model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }
}
