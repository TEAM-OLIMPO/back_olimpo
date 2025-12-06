package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.UserRegistrationRequest;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register/vendor")
    public ResponseEntity<User> registrarVendedor(@RequestBody UserRegistrationRequest request) {
        User user = userService.registrarVendedor(request.getNombre(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/buyer")
    public ResponseEntity<User> registrarComprador(@RequestBody UserRegistrationRequest request) {
        User user = userService.registrarComprador(request.getNombre(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }
}
