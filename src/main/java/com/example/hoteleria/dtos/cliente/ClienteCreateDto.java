package com.example.hoteleria.dtos.cliente;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ClienteCreateDto {
    @NotEmpty(message = "El nombre no puede ser nulo o estar vacio")
    private String nombre;
    @NotEmpty(message = "El apellido no puede ser nulo o estar vacio")
    private String apellido;
    @Email(message = "El email no es valido")
    private String email;
    @NotEmpty(message = "El telefono no puede ser nulo o estar vacio")
    private String telefono;
    @NotEmpty(message = "La contraseña no puede ser nula o estar vacia")
    private String password;
    @Size(min = 1,message = "Al menos debe contener 1 rol")
    private List<@Positive(message = "Lo valores de rol deben ser positivos") Long> roles;
}
