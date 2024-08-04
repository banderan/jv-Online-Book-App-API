package org.example.jvspringbootfirstbook.service.cart;

import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.User;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartDto findUserCart(User user, Pageable pageable);

    ShoppingCartDto addItem(User user, Long bookId, CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto update(Long itemId, CartItemRequestDto cartItemRequestDto);

    void deleteById(User user, Long itemId);
}
