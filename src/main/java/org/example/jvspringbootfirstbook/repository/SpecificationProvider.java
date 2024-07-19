package org.example.jvspringbootfirstbook.repository;

import org.example.jvspringbootfirstbook.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<T> getSpecification(String[] param);
}
