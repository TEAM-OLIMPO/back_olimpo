package com.olimpo.olimpo.services;

import com.olimpo.olimpo.exceptions.ResourceNotFoundException;
import com.olimpo.olimpo.models.User;
import com.olimpo.olimpo.models.UserRole;
import com.olimpo.olimpo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registrarVendedor(String nombre, String email, String password) {
        User user = new User();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(password); // TODO: en proyecto real, encriptar
        user.setRole(UserRole.VENDEDOR);
        return userRepository.save(user);
    }

    public User registrarComprador(String nombre, String email, String password) {
        User user = new User();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.COMPRADOR);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    // 🔐 Login sencillo por email + password
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        if (!user.getPassword().equals(password)) {
            // aquí podrías lanzar tu propia excepción, por ahora usamos IllegalArgumentException
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return user;
    }
}
