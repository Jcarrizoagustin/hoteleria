package com.example.hoteleria.dtos.habitacion;

import com.example.hoteleria.dtos.descripcion.DescripcionCreateDto;
import lombok.Data;

import java.util.List;

@Data
public class HabitacionCreateDto {
    private Integer capacidad;
    private Double precio;
    private List<DescripcionCreateDto> descripciones;
}
