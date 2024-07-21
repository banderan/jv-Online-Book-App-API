package org.example.jvspringbootfirstbook.dto;

public record BookSearchParametersDto(
        String[] title,
        String[] author,
        String[] isbn
) {
}
