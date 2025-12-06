package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.models.Purchase;
import com.olimpo.olimpo.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/buyer/{buyerId}/basket/{basketId}")
    public ResponseEntity<Purchase> comprarCanasta(@PathVariable Long buyerId,
                                                   @PathVariable Long basketId,
                                                   @RequestParam(defaultValue = "1") int cantidad) {
        Purchase purchase = purchaseService.comprarCanasta(buyerId, basketId, cantidad);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/vendor/{vendorId}/history")
    public ResponseEntity<List<Purchase>> historialVentasVendedor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(purchaseService.historialVentasVendedor(vendorId));
    }
}
