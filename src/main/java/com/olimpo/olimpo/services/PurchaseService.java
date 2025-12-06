package com.olimpo.olimpo.services;

import com.olimpo.olimpo.exceptions.ResourceNotFoundException;
import com.olimpo.olimpo.models.*;
import com.olimpo.olimpo.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final BasketService basketService;
    private final UserService userService;
    private final NotificationService notificationService;

    public Purchase comprarCanasta(Long buyerId, Long basketId, int cantidad) {
        User buyer = userService.getById(buyerId);
        Basket basket = basketService.getById(basketId);

        if (buyer.getRole() != UserRole.COMPRADOR) {
            throw new IllegalArgumentException("El usuario no es un comprador válido");
        }

        basketService.disminuirStock(basket, cantidad);

        Purchase purchase = new Purchase();
        purchase.setBasket(basket);
        purchase.setBuyer(buyer);
        purchase.setCantidad(cantidad);
        purchase.setFechaCompra(LocalDateTime.now());
        purchase.setStatus(PurchaseStatus.PAGADA);

        BigDecimal total = basket.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        purchase.setTotal(total);

        Purchase saved = purchaseRepository.save(purchase);

        User vendor = basket.getVendor();
        String mensaje = "Has recibido una nueva compra de tu canasta '" +
                basket.getTitulo() + "' por parte de " + buyer.getNombre() +
                ". Cantidad: " + cantidad + ", Total: " + total;
        notificationService.notificarUsuario(vendor, mensaje);

        return saved;
    }

    public List<Purchase> historialVentasVendedor(Long vendorId) {
        User vendor = userService.getById(vendorId);
        return purchaseRepository.findTop10ByBasket_VendorOrderByFechaCompraDesc(vendor);
    }

    public Purchase getById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con id: " + purchaseId));
    }

    public List<Purchase> historialComprasComprador(Long buyerId) {
    User buyer = userService.getById(buyerId);
    return purchaseRepository.findByBuyerOrderByFechaCompraDesc(buyer);
}

}
