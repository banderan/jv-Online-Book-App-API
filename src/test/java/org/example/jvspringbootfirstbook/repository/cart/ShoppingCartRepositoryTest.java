package org.example.jvspringbootfirstbook.repository.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.model.ShoppingCart;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    public static final String EMAIL = "okok@email.com";
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("""
            Should return the ShoppingCart associated with the User having ID 2
            """)
    @Sql(scripts = {"classpath:db/cart/repository/delete_existing_carts.sql",
            "classpath:db/cart/repository/add_shopping_cart_with_necessities.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:db/cart/repository/delete_existing_carts.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findShoppingCartByUser_UserWithIdEqualOne_returnsShoppingCart() {
        User user = userRepository.findByEmail(EMAIL).orElseThrow(
                () -> new EntityNotFoundException("User with email " + EMAIL + " not found")
        );

        ShoppingCart shoppingCartByUser = shoppingCartRepository.findShoppingCartByUser(user.getId());
        Assertions.assertAll(
                () -> assertNotNull(shoppingCartByUser),
                () -> assertEquals(user, shoppingCartByUser.getUser()),
                () -> assertEquals(
                        getTestShoppingCart(user.getId()),
                        shoppingCartByUser)
        );
    }

    private ShoppingCart getTestShoppingCart(long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(userId);
        shoppingCart.setUser(userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        ));
        shoppingCart.setCartItems(Set.of());
        shoppingCart.setDeleted(false);
        return shoppingCart;
    }
}
