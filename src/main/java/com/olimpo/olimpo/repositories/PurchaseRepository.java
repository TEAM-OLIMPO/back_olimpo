package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.models.Purchase;
import com.olimpo.olimpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findTop10ByBasket_VendorOrderByFechaCompraDesc(User vendor);
}
