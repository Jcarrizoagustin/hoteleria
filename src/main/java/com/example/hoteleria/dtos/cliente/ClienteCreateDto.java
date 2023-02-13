package com.example.hoteleria.dtos.cliente;

import lombok.Data;

import java.util.List;

@Data
public class ClienteCreateDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;
    private List<Long> roles;
}
