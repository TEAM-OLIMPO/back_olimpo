package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.models.Notification;
import com.olimpo.olimpo.services.NotificationService;
import com.olimpo.olimpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> obtenerNotificaciones(@PathVariable Long userId) {
        List<Notification> notificaciones = notificationService.obtenerNotificacionesUsuario(userId, userService);
        return ResponseEntity.ok(notificaciones);
    }
}
