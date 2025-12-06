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
        user.setPassword(password); // pendiente encriptar
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
}
