package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.services.PurchaseService;
import com.olimpo.olimpo.services.RatingService;
import com.olimpo.olimpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final UserService userService;
    private final PurchaseService purchaseService;
    private final RatingService ratingService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<Map<String, Object>> perfil(@PathVariable Long userId) {
        User user = userService.getById(userId);

        if (user.getRole().name().equals("VENDEDOR")) {
            var ventas = purchaseService.historialVentasVendedor(userId);
            var resumenRatings = ratingService.resumenVendedor(userId);
            return ResponseEntity.ok(Map.of(
                    "user", user,
                    "historialVentas", ventas,
                    "reputacion", resumenRatings
            ));
        } else {
            var compras = purchaseService.historialComprasComprador(userId);
            return ResponseEntity.ok(Map.of(
                    "user", user,
                    "historialCompras", compras
            ));
        }
    }
}
