package org.example.jvspringbootfirstbook.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.exception.DataProcessingException;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.SpecificationProvider;
import org.example.jvspringbootfirstbook.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst().orElseThrow(
                        () -> new DataProcessingException("Could not find specification provider for key: " + key)
                );
    }
}
