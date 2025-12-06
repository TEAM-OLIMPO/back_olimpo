package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.CreateBasketRequest;
import com.olimpo.olimpo.models.Basket;
import com.olimpo.olimpo.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/vendor/{vendorId}")
public ResponseEntity<Basket> crearCanasta(@PathVariable Long vendorId,
                                           @RequestBody CreateBasketRequest request) {
    Basket basket = basketService.crearCanasta(
            vendorId,
            request.getTitulo(),
            request.getDescripcion(),
            request.getPrecio(),
            request.getCantidadDisponible(),
            request.getImageUrl(),
            request.getFechaRecogida(),
            request.getHoraInicioRecogida(),
            request.getHoraFinRecogida(),
            request.getMaxPorUsuario()
    );
    return ResponseEntity.ok(basket);
}


    @GetMapping
    public ResponseEntity<List<Basket>> listarCanastasActivas() {
        return ResponseEntity.ok(basketService.listarCanastasActivas());
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Basket>> listarCanastasPorVendedor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(basketService.listarCanastasPorVendedor(vendorId));
    }

    @GetMapping("/near")
public ResponseEntity<List<Basket>> listarCanastasCercanas(@RequestParam double lat,
                                                           @RequestParam double lon,
                                                           @RequestParam(defaultValue = "5") double radioKm) {
    return ResponseEntity.ok(basketService.listarCanastasCercanas(lat, lon, radioKm));
}

}
