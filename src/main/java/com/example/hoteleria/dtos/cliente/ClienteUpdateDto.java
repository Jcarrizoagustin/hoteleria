package com.example.hoteleria.dtos.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClienteUpdateDto {
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
}
