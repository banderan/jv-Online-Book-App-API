package org.example.jvspringbootfirstbook.service.cart;

import org.example.jvspringbootfirstbook.dto.cart.CartItemDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemUpdatedDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.mapper.CartItemMapper;
import org.example.jvspringbootfirstbook.mapper.ShoppingCartMapper;
import org.example.jvspringbootfirstbook.model.Book;
import org.example.jvspringbootfirstbook.model.Category;
import org.example.jvspringbootfirstbook.model.Role;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.book.BookRepository;
import org.example.jvspringbootfirstbook.repository.cart.CartItemRepository;
import org.example.jvspringbootfirstbook.repository.cart.ShoppingCartRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static final long ID = 1L;
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String SHIPPING_ADDRESS = "address";
    private static final Set<Role> EMPTY_ROLES = Set.of();
    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final String ISBN = "123123";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123.12);
    private static final String DESCRIPTION = "Description";
    private static final String COVER_IMAGE = "Cover Image";
    private static final Set<Category> EMPTY_CATEGORIES = Set.of();
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartItemMapper cartItemMapper;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    void name() {

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
