package com.olimpo.olimpo.services;

import com.olimpo.olimpo.models.Rating;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final PurchaseService purchaseService;

    public Rating calificarVendedor(Long buyerId, Long vendorId, Long purchaseId, int estrellas, String comentario) {
        User buyer = userService.getById(buyerId);
        User vendor = userService.getById(vendorId);
        var purchase = purchaseService.getById(purchaseId);

        if (estrellas < 1 || estrellas > 5) {
            throw new IllegalArgumentException("Las estrellas deben estar entre 1 y 5");
        }

        Rating rating = new Rating();
        rating.setBuyer(buyer);
        rating.setVendor(vendor);
        rating.setPurchase(purchase);
        rating.setEstrellas(estrellas);
        rating.setComentario(comentario);
        rating.setFechaCreacion(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    public List<Rating> listarRatingsVendedor(Long vendorId) {
        User vendor = userService.getById(vendorId);
        return ratingRepository.findByVendorOrderByFechaCreacionDesc(vendor);
    }

    public Map<String, Object> resumenVendedor(Long vendorId) {
        User vendor = userService.getById(vendorId);
        List<Rating> ratings = ratingRepository.findByVendorOrderByFechaCreacionDesc(vendor);

        // 🔧 AQUÍ ESTABA EL PROBLEMA: usar IntSummaryStatistics
        IntSummaryStatistics stats = ratings.stream()
                .mapToInt(Rating::getEstrellas)
                .summaryStatistics();

        double promedio = stats.getCount() == 0 ? 0.0 : stats.getAverage();

        return Map.of(
                "vendorId", vendorId,
                "totalCalificaciones", stats.getCount(),
                "promedioEstrellas", promedio,
                "ratings", ratings
        );
    }
}
