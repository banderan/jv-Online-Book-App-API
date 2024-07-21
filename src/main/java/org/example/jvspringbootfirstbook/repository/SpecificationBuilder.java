package org.example.jvspringbootfirstbook.repository;

import org.example.jvspringbootfirstbook.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto searchParametersDto);
}
