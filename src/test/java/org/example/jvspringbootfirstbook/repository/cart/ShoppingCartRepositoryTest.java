package org.example.jvspringbootfirstbook.repository.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.api.AssertionsForClassTypes;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.model.Role;
import org.example.jvspringbootfirstbook.model.RoleName;
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
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

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
        int userId = 2;
        ShoppingCart testShoppingCart = getTestShoppingCart();
        ShoppingCart actual = shoppingCartRepository.findShoppingCartByUser(testShoppingCart.getUser().getId());
        AssertionsForClassTypes.assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testShoppingCart);
    }

    private ShoppingCart getTestShoppingCart() {
        Role role = new Role();
        role.setName(RoleName.ROLE_USER);

        User user = new User();
        user.setId(2L);
        user.setEmail("okok@email.com");
        user.setPassword("$2a$10$EhBFr.PagMjT0P0EYqRL/.KjPUA2vRSutGZo92Xr9Hh/JwwAJq/vi");//password
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        user.setShippingAddress("address");
        user.setDeleted(false);
        user.setRoles(Set.of(role));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(2L);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(Set.of());
        shoppingCart.setDeleted(false);
        return shoppingCart;
    }
}
