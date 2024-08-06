package org.example.jvspringbootfirstbook.dto.cart;

import jakarta.validation.constraints.Positive;

public record CartItemUpdatedDto(
        @Positive
        int quantity
) {
}
