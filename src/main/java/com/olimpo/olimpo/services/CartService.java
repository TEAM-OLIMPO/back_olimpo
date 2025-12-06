package com.olimpo.olimpo.services;

import com.olimpo.olimpo.models.*;
import com.olimpo.olimpo.repositories.CartItemRepository;
import com.olimpo.olimpo.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final BasketService basketService;
    private final PurchaseService purchaseService;

    private Cart obtenerOCrearCarrito(User buyer) {
        return cartRepository.findByBuyer(buyer)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setBuyer(buyer);
                    c.setItems(new ArrayList<>());
                    return cartRepository.save(c);
                });
    }

    public Cart agregarItemAlCarrito(Long buyerId, Long basketId, int cantidad) {
        User buyer = userService.getById(buyerId);
        Basket basket = basketService.getById(basketId);

        Cart cart = obtenerOCrearCarrito(buyer);

        CartItem item = new CartItem();
        item.setBasket(basket);
        item.setCantidad(cantidad);
        item.setCart(cart);

        cart.getItems().add(item);
        cartItemRepository.save(item);

        return cartRepository.save(cart);
    }

    public Cart obtenerCarrito(Long buyerId) {
        User buyer = userService.getById(buyerId);
        return cartRepository.findByBuyer(buyer)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setBuyer(buyer);
                    c.setItems(new ArrayList<>());
                    return cartRepository.save(c);
                });
    }

    public void checkout(Long buyerId) {
        Cart cart = obtenerCarrito(buyerId);

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return;
        }

        cart.getItems().forEach(item ->
                purchaseService.comprarCanasta(buyerId, item.getBasket().getId(), item.getCantidad())
        );

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
