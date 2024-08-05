package org.example.jvspringbootfirstbook.repository.cart;

import org.example.jvspringbootfirstbook.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>,
        JpaSpecificationExecutor<ShoppingCart> {

    @EntityGraph(attributePaths = {"user", "cartItems"})
    ShoppingCart findShoppingCartByUserId(Long userId);
}
