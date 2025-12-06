package com.olimpo.olimpo.services;

import com.olimpo.olimpo.exceptions.ResourceNotFoundException;
import com.olimpo.olimpo.models.Basket;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.models.UserRole;
import com.olimpo.olimpo.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                               int cantidadDisponible) {

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
}
