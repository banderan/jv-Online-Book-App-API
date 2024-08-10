package org.example.jvspringbootfirstbook.repository.book;

import java.util.List;
import org.example.jvspringbootfirstbook.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :categoriesId")
    List<Book> findAllByCategoriesId(Long categoriesId, Pageable pageable);
}
