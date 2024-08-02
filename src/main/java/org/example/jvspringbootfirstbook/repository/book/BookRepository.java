package org.example.jvspringbootfirstbook.repository.book;

import org.example.jvspringbootfirstbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :categoriesId")
    List<Book> findAllByCategoriesId(Long categoriesId);
}
