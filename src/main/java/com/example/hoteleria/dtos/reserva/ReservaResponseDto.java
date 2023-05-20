package com.example.hoteleria.dtos.reserva;

import com.example.hoteleria.dtos.habitacion.HabitacionResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class ReservaResponseDto {
    private Long id;
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String fechaIngreso;
    private List<HabitacionResponseDto> habitaciones;
    private String fechaSalida;
    private String precioTotal;
}
