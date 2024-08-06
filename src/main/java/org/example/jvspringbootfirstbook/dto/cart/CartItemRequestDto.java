package org.example.jvspringbootfirstbook.dto.cart;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CartItemRequestDto(
        @Positive
        int quantity,
        @PositiveOrZero
        Long bookId
) {
}
