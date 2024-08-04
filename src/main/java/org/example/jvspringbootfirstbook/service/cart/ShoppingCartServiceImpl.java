package org.example.jvspringbootfirstbook.service.cart;

import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.cart.CartItemRepository;
import org.example.jvspringbootfirstbook.repository.cart.ShoppingCartRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto findUserCart(User user, Pageable pageable) {
        return null;
    }

    @Override
    public ShoppingCartDto addItem(User user, Long bookId, CartItemRequestDto cartItemRequestDto) {
        return null;
    }

    @Override
    public ShoppingCartDto update(Long itemId, CartItemRequestDto cartItemRequestDto) {
        return null;
    }

    @Override
    public void deleteById(User user, Long itemId) {

    }
}
