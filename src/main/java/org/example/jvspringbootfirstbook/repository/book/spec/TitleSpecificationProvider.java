package org.example.jvspringbootfirstbook.repository.book.spec;

import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String[] param) {
        return ((root, query, criteriaBuilder) -> root.get("title")
                .in(Arrays.stream(param).toArray()));
    }
}
