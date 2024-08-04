package org.example.jvspringbootfirstbook.dto.cart;

import java.util.Set;
import lombok.Data;
import org.example.jvspringbootfirstbook.model.CartItem;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
