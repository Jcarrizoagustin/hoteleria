package com.example.hoteleria.dtos.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank(message = "El email es requerido") @NotNull(message = "El email es requerido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")  @NotNull(message = "La contraseña es requerida")
    private String password;
}
