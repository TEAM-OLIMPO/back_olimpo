package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.models.Cart;
import com.olimpo.olimpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByBuyer(User buyer);
}
