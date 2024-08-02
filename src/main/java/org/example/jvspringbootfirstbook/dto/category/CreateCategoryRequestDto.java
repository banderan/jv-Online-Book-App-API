package org.example.jvspringbootfirstbook.dto.category;

public record CreateCategoryRequestDto(
        String name,
        String description
) {
}
