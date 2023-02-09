package com.example.hoteleria.dtos.cliente;

import lombok.Data;

@Data
public class ClienteCreateDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
