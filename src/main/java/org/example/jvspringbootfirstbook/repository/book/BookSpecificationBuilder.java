package org.example.jvspringbootfirstbook.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.BookSearchParametersDto;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.SpecificationBuilder;
import org.example.jvspringbootfirstbook.repository.SpecificationProvider;
import org.example.jvspringbootfirstbook.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (searchParametersDto.coverImage() != null && searchParametersDto.coverImage().length > 0) {
            specification = specification.and(specificationProviderManager.getSpecificationProvider("coverImage")
                    .getSpecification(searchParametersDto.coverImage()));
        }
        if (searchParametersDto.title() != null && searchParametersDto.title().length > 0) {
            specification = specification.and(specificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParametersDto.title()));
        }
        if (searchParametersDto.author() != null && searchParametersDto.author().length > 0) {
            specification = specification.and(specificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParametersDto.author()));
        }
        if (searchParametersDto.isbn() != null && searchParametersDto.isbn().length > 0) {
            specification = specification.and(specificationProviderManager.getSpecificationProvider("isbn")
                    .getSpecification(searchParametersDto.isbn()));
        }
        return specification;
    }
}
