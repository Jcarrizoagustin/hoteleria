package com.example.hoteleria.dtos.habitacion;

import com.example.hoteleria.dtos.descripcion.DescripcionResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class HabitacionResponseDto {

    private Long id;
    private String nombre;
    private Integer capacidad;
    private Integer cantidadCamas;
    private String urlImg;
    private List<DescripcionResponseDto> descripciones;
    private String precio;
}
