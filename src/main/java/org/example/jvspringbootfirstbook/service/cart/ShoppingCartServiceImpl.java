package org.example.jvspringbootfirstbook.service.cart;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.cart.CartItemDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemUpdatedDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.mapper.CartItemMapper;
import org.example.jvspringbootfirstbook.mapper.ShoppingCartMapper;
import org.example.jvspringbootfirstbook.model.CartItem;
import org.example.jvspringbootfirstbook.model.ShoppingCart;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.book.BookRepository;
import org.example.jvspringbootfirstbook.repository.cart.CartItemRepository;
import org.example.jvspringbootfirstbook.repository.cart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto findUserCart(User user) {
        return shoppingCartMapper.toDto(shoppingCartRepository
                .findShoppingCartByUser(user));
    }

    @Override
    public ShoppingCartDto addItem(User user, CartItemRequestDto cartItemRequestDto) {
        CartItem item = cartItemMapper.toModel(cartItemRequestDto);
        item.setBook(bookRepository.findById(item.getBook().getId())
                .orElseThrow(EntityNotFoundException::new));
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUser(user);
        item.setShoppingCart(cart);
        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(item);
        cart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(cart));
    }

    @Override
    public CartItemDto update(Long itemId,
                              CartItemUpdatedDto updatedDto) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException("Item not found"
                        + " with item id: " + itemId)
        );
        cartItem.setQuantity(
                updatedDto.quantity()
        );
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteById(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}
