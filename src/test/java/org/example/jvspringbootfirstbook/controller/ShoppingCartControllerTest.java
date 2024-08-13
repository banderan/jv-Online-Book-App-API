package org.example.jvspringbootfirstbook.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.example.jvspringbootfirstbook.dto.cart.CartItemDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.CartItemUpdatedDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.*;
import org.example.jvspringbootfirstbook.repository.cart.CartItemRepository;
import org.example.jvspringbootfirstbook.service.cart.ShoppingCartService;
import org.example.jvspringbootfirstbook.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    private static final String AUTHOR = "author";
    private static final String ISBN = "1231231";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123.12);
    private static final String DESCRIPTION = "Description";
    private static final String COVER_IMAGE = "Cover Image";
    private static final Set<Category> EMPTY_CATEGORIES = new HashSet<>();
    private static final Set<CartItem> EMPTY_CART_ITEMS = new HashSet<>();
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;


    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("db/cart/controller/delete-carts.sql")
            );
        }
    }

    /*@Test
    @DisplayName("""
            Update item in cart with valid input returns CartItemDto
            """)
    @WithMockUser(username = "user", roles = "USER")
    void updateItemCart_Success() throws Exception {
        //Given
        long quantity = QUANTITY + 1;
        CartItemUpdatedDto cartItemUpdateDto = getCartItemUpdateDto((int) quantity);
        userService.register();

        CartItem cartItem = getCartItem(
                getShoppingCart(getUser()), getBook());
        CartItemRequestDto cartItemRequestDto = getCartItemRequestDto(QUANTITY, getBook());
        shoppingCartService.addItem(getUser(), cartItemRequestDto);
        Long cartItemId = cartItem.getId();

        CartItemDto expected = getCartItemDto(
                cartItem.getBook(), (long) cartItemUpdateDto.quantity());

        String jsonRequest = objectMapper.writeValueAsString(cartItemUpdateDto);
        //When
        MvcResult result = mockMvc.perform(
                        put("/api/cart/cart-items/" + cartItemId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //Then
        CartItemDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CartItemDto.class
        );
        Assertions.assertEquals(quantity, actual.quantity());
    }*/

    @Test
    @DisplayName("""
            Delete item from cart with valid input
            """)
    @WithMockUser(username = "user", roles = "USER")
    void deleteItemFromCart_Success() throws Exception {
        CartItem cartItem = getCartItem(
                getShoppingCart(getUser()), getBook());
        Long cartItemId = cartItem.getId();
        mockMvc.perform(delete("/cart/cart-items/" + cartItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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
