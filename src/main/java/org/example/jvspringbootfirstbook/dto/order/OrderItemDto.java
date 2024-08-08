package org.example.jvspringbootfirstbook.dto.order;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemDto(
        @Positive
        Long id,
        @Positive
        Long bookId,
        @PositiveOrZero
        int quantity) {
}
