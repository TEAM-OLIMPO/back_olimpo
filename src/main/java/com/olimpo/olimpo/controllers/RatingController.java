package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.RatingRequest;
import com.olimpo.olimpo.models.Rating;
import com.olimpo.olimpo.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/buyer/{buyerId}/vendor/{vendorId}")
    public ResponseEntity<Rating> calificar(@PathVariable Long buyerId,
                                            @PathVariable Long vendorId,
                                            @RequestBody RatingRequest request) {
        Rating rating = ratingService.calificarVendedor(
                buyerId,
                vendorId,
                request.getPurchaseId(),
                request.getEstrellas(),
                request.getComentario()
        );
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Rating>> listarRatingsVendedor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(ratingService.listarRatingsVendedor(vendorId));
    }

    @GetMapping("/vendor/{vendorId}/summary")
    public ResponseEntity<Map<String, Object>> resumenVendedor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(ratingService.resumenVendedor(vendorId));
    }
}
