package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.models.Basket;
import com.olimpo.olimpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    List<Basket> findByActivaTrue();

    List<Basket> findByVendor(User vendor);
}
