package org.example.jvspringbootfirstbook.dto.book;

public record BookSearchParametersDto(
        String[] title,
        String[] author,
        String[] isbn
) {
}
