package org.example.jvspringbootfirstbook.dto.cart;

import jakarta.validation.constraints.NotNull;

public record CartItemRequestDto(
        @NotNull
        int quantity,
        @NotNull
        Long bookId
) {
}
