package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.AddToCartRequest;
import com.olimpo.olimpo.models.Cart;
import com.olimpo.olimpo.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/buyer/{buyerId}/items")
    public ResponseEntity<Cart> agregarAlCarrito(@PathVariable Long buyerId,
                                                 @RequestBody AddToCartRequest request) {
        Cart cart = cartService.agregarItemAlCarrito(
                buyerId,
                request.getBasketId(),
                request.getCantidad()
        );
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<Cart> obtenerCarrito(@PathVariable Long buyerId) {
        return ResponseEntity.ok(cartService.obtenerCarrito(buyerId));
    }

    @PostMapping("/buyer/{buyerId}/checkout")
    public ResponseEntity<Void> checkout(@PathVariable Long buyerId) {
        cartService.checkout(buyerId);
        return ResponseEntity.ok().build();
    }
}
