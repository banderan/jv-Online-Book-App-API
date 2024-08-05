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

    @Override
    public ShoppingCartDto findUserCart(User user) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findShoppingCartByUserId(
                user.getId()
        ));
    }

    @Override
    public ShoppingCartDto addItem(User user, CartItemRequestDto cartItemRequestDto) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserId(user.getId());
        CartItem cartItem = cartItemMapper.toModelFromRequest(cartItemRequestDto);
        cartItem.setShoppingCart(cart);
        CartItem save = cartItemRepository.save(cartItem);
        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(save);
        cart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(cart);
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
