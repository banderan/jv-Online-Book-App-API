package org.example.jvspringbootfirstbook.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.example.jvspringbootfirstbook.dto.cart.CartItemDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemUpdatedDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.model.CartItem;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.model.Role;
import org.example.jvspringbootfirstbook.model.ShoppingCart;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.service.cart.ShoppingCartService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {
    public static final int QUANTITY = 1;
    private static final long ID = 1L;
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String SHIPPING_ADDRESS = "address";
    private static final Set<Role> EMPTY_ROLES = new HashSet<>();
    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final String ISBN = "123123";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123.12);
    private static final String DESCRIPTION = "Description";
    private static final String COVER_IMAGE = "Cover Image";
    private static final Set<Category> EMPTY_CATEGORIES = new HashSet<>();
    private static final Set<CartItem> EMPTY_CART_ITEMS = new HashSet<>();

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Test
    @DisplayName("""
            Update item in cart with valid input returns CartItemDto
            """)
    void updateItemCart_Success() {
        int newQuantity = QUANTITY + 1;
        User user = getUser();
        ShoppingCart shoppingCart = getShoppingCart(user);
        Book book = getBook();
        CartItem cartItem = getCartItem(shoppingCart, book);

        CartItemUpdatedDto cartItemUpdateDto = getCartItemUpdateDto(newQuantity);
        CartItemDto expected = getCartItemDto(book, (long) newQuantity);

        when(shoppingCartService.updateItemQuantity(cartItem.getId(), cartItemUpdateDto))
                .thenReturn(expected);

        CartItemDto actual = shoppingCartController
                .updateItemInCart(cartItemUpdateDto, cartItem.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);

        verify(shoppingCartService, times(1))
                .updateItemQuantity(cartItem.getId(), cartItemUpdateDto);
        verifyNoMoreInteractions(shoppingCartService);

    }

    @Test
    @DisplayName("""
            Delete item from cart with valid input
            """)
    void deleteItemFromCart_Success() {
        Long cartItemId = 1L;
        doNothing().when(shoppingCartService).deleteById(cartItemId);

        shoppingCartController.deleteItemFromCart(cartItemId);

        verify(shoppingCartService, times(1)).deleteById(cartItemId);
        verifyNoMoreInteractions(shoppingCartService);
    }

    private static @NotNull CartItem getCartItem(
            ShoppingCart shoppingCart, Book book, CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(ID);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.quantity());
        cartItem.setDeleted(false);
        return cartItem;
    }

    private static @NotNull CartItem getCartItem(ShoppingCart shoppingCart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setId(ID);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(QUANTITY);
        cartItem.setDeleted(false);
        return cartItem;
    }

    private static @NotNull User getUser() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setShippingAddress(SHIPPING_ADDRESS);
        user.setRoles(EMPTY_ROLES);
        user.setDeleted(false);
        return user;
    }

    private static @NotNull ShoppingCart getShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ID);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(EMPTY_CART_ITEMS);
        shoppingCart.setDeleted(false);
        return shoppingCart;
    }

    private static @NotNull ShoppingCartDto getShoppingCartDto(
            User user,
            Set<CartItemDto> cartItemDto) {
        return new ShoppingCartDto(
                ID,
                user.getId(),
                cartItemDto
        );
    }

    private static @NotNull Book getBook() {
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setIsbn(ISBN);
        book.setPrice(PRICE);
        book.setDescription(DESCRIPTION);
        book.setCoverImage(COVER_IMAGE);
        book.setCategories(EMPTY_CATEGORIES);
        book.setDeleted(false);
        return book;
    }

    private static @NotNull CartItemDto getCartItemDto(Book book, Long quantity) {
        return new CartItemDto(
                ID,
                book.getId(),
                book.getTitle(),
                quantity
        );
    }

    private static @NotNull CartItemRequestDto getCartItemRequestDto(
            int quantity,
            Book book) {
        return new CartItemRequestDto(
                quantity,
                book.getId()
        );
    }

    private static @NotNull CartItemUpdatedDto getCartItemUpdateDto(int quantity) {
        return new CartItemUpdatedDto(quantity);
    }
}
