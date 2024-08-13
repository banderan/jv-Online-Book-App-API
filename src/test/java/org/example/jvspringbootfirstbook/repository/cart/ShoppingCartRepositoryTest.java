package org.example.jvspringbootfirstbook.repository.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.model.ShoppingCart;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("""
            Should return the ShoppingCart associated with the User having ID 1
            """)
    /*@Sql(scripts = {"classpath:db/cart/repository/add_shopping_cart_with_necessities.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:db/cart/repository/delete_shopping_cart_with_necessities.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)*/
    void findShoppingCartByUser_UserWithIdEqualOne_returnsShoppingCart() {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new EntityNotFoundException("User with id " + 1L + " not found")
        );

        ShoppingCart shoppingCartByUser = shoppingCartRepository.findShoppingCartByUser(user);

        Assertions.assertAll(
                () -> assertNotNull(shoppingCartByUser),
                () -> assertEquals(user, shoppingCartByUser.getUser())
        );
    }
}
