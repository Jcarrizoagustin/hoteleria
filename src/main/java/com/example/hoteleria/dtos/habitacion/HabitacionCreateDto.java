package com.example.hoteleria.dtos.habitacion;

import com.example.hoteleria.dtos.descripcion.DescripcionCreateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class HabitacionCreateDto {
    @Min(value = 2,message = "La capacidad debe ser minimo para 2 personas")
    @Positive(message = "La capacidad no puede ser negativa")
    @NotNull(message = "La capacidad no puede ser nula")
    private Integer capacidad;
    private String nombre;
    private Integer cantidadCamas;
    private String urlImg;
    @Positive(message = "El precio no pude ser negativo")
    private Double precio;
    private List<DescripcionCreateDto> descripciones;
}
