package com.olimpo.olimpo.services;

import com.olimpo.olimpo.models.Notification;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void notificarUsuario(User user, String mensaje) {
        Notification n = new Notification();
        n.setUser(user);
        n.setMensaje(mensaje);
        n.setFechaCreacion(LocalDateTime.now());
        n.setLeida(false);
        notificationRepository.save(n);
    }

    public List<Notification> obtenerNotificacionesUsuario(Long userId, UserService userService) {
        User user = userService.getById(userId);
        return notificationRepository.findByUserOrderByFechaCreacionDesc(user);
    }
}
