package org.example.jvspringbootfirstbook.dto.cart;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        Long quantity
) {
}
