package com.olimpo.olimpo.services;

import com.olimpo.olimpo.exceptions.ResourceNotFoundException;
import com.olimpo.olimpo.models.Basket;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.models.UserRole;
import com.olimpo.olimpo.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;

    public Basket crearCanasta(Long vendorId,
                           String titulo,
                           String descripcion,
                           BigDecimal precio,
                           int cantidadDisponible,
                           String imageUrl,
                           LocalDate fechaRecogida,
                           LocalTime horaInicioRecogida,
                           LocalTime horaFinRecogida,
                           Integer maxPorUsuario) {

    User vendor = userService.getById(vendorId);
    if (vendor.getRole() != UserRole.VENDEDOR) {
        throw new IllegalArgumentException("El usuario no es un vendedor válido");
    }

    Basket basket = new Basket();
    basket.setVendor(vendor);
    basket.setTitulo(titulo);
    basket.setDescripcion(descripcion);
    basket.setPrecio(precio);
    basket.setCantidadDisponible(cantidadDisponible);
    basket.setImageUrl(imageUrl);
    basket.setFechaRecogida(fechaRecogida);
    basket.setHoraInicioRecogida(horaInicioRecogida);
    basket.setHoraFinRecogida(horaFinRecogida);
    basket.setMaxPorUsuario(maxPorUsuario);
    basket.setActiva(true);
    basket.setFechaCreacion(LocalDateTime.now());

    return basketRepository.save(basket);
}

    public List<Basket> listarCanastasActivas() {
        return basketRepository.findByActivaTrue();
    }

    public List<Basket> listarCanastasPorVendedor(Long vendorId) {
        User vendor = userService.getById(vendorId);
        return basketRepository.findByVendor(vendor);
    }

    public Basket getById(Long basketId) {
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Canasta no encontrada con id: " + basketId));
    }

    public void disminuirStock(Basket basket, int cantidad) {
        if (basket.getCantidadDisponible() < cantidad) {
            throw new IllegalArgumentException("No hay stock suficiente de la canasta");
        }
        basket.setCantidadDisponible(basket.getCantidadDisponible() - cantidad);
        if (basket.getCantidadDisponible() == 0) {
            basket.setActiva(false);
        }
        basketRepository.save(basket);
    }

    public List<Basket> listarCanastasCercanas(double lat, double lon, double radioKm) {
    List<Basket> activas = basketRepository.findByActivaTrue();

    return activas.stream()
            .filter(b -> b.getVendor().getLatitud() != null && b.getVendor().getLongitud() != null)
            .filter(b -> distanceKm(
                    lat, lon,
                    b.getVendor().getLatitud(),
                    b.getVendor().getLongitud()
            ) <= radioKm)
            .sorted(Comparator.comparingDouble(b ->
                    distanceKm(lat, lon,
                            b.getVendor().getLatitud(),
                            b.getVendor().getLongitud())
            ))
            .toList();
}

// Haversine simple
private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
    double R = 6371;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

}
