package org.example.jvspringbootfirstbook.dto.cart;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long quantity;
}
