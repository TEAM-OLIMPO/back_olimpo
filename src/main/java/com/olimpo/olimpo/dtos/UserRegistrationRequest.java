package com.olimpo.olimpo.dtos;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String nombre;
    private String email;
    private String password;
}
