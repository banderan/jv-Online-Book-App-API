package org.example.jvspringbootfirstbook.service.cart;

import org.example.jvspringbootfirstbook.dto.cart.CartItemDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemUpdatedDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findUserCart(User user);

    ShoppingCartDto addItem(User user, CartItemRequestDto cartItemRequestDto);

    CartItemDto update(Long itemId, CartItemUpdatedDto updatedDto);

    void deleteById(Long itemId);
}
