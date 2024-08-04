package org.example.jvspringbootfirstbook.dto.cart;

import lombok.Data;
import org.example.jvspringbootfirstbook.model.CartItem;

import java.util.Set;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
