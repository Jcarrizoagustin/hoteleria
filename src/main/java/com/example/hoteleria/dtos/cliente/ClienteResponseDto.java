package com.example.hoteleria.dtos.cliente;

import lombok.Data;

@Data
public class ClienteResponseDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}
