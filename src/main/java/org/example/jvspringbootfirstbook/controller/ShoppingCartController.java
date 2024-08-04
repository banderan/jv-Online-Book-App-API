package org.example.jvspringbootfirstbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.cart.CartItemRequestDto;
import org.example.jvspringbootfirstbook.dto.cart.ShoppingCartDto;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.service.cart.ShoppingCartService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shopping cart management",
        description = "Endpoints for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get user Cart",
            description = "get a cart your user")
    public ShoppingCartDto getUserCart(Authentication authentication, Pageable pageable) {
        User principal = (User) authentication.getPrincipal();
        return shoppingCartService.findUserCart(principal, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "adding item to user cart",
            description = "create new item and add to user cart")
    public ShoppingCartDto addItemToUserCart(Authentication authentication,
                                             @RequestBody Long bookId,
                                             @RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        User principal = (User) authentication.getPrincipal();
        return shoppingCartService.addItem(principal,bookId ,cartItemRequestDto);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Update item",
            description = "update item in user cart with your id")
    public ShoppingCartDto updateItemInCart(@RequestBody @Valid CartItemRequestDto cartItemRequestDto,
                                            @PathVariable Long id) {
        return shoppingCartService.update(id, cartItemRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Delete item ",
            description = "delete item from cart with your id")
    public void deleteItemFromCart(Authentication authentication, @PathVariable Long id) {
        User principal = (User) authentication.getPrincipal();
        shoppingCartService.deleteById(principal, id);
    }
}
