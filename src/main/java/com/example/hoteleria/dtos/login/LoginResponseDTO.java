package com.example.hoteleria.dtos.login;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String role ="user";
}
