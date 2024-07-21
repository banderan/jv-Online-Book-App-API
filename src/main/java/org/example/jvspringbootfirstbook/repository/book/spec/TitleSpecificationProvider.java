package org.example.jvspringbootfirstbook.repository.book.spec;

import java.util.Arrays;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_NAME = "title";

    @Override
    public String getKey() {
        return FIELD_NAME;
    }

    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get("title")
                .in(Arrays.stream(params).toArray()));
    }
}
