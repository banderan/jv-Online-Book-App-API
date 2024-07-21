package org.example.jvspringbootfirstbook.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.exception.ProviderException;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.repository.SpecificationProvider;
import org.example.jvspringbootfirstbook.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst().orElseThrow(
                        () -> new ProviderException(
                                "Could not find specification provider for key: " + key)
                );
    }
}
