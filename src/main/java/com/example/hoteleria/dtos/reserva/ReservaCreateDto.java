package com.example.hoteleria.dtos.reserva;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservaCreateDto {
    private Long idCliente;
    private List<Long> idHabitaciones;
    @JsonFormat(pattern ="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy",iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaIngreso;
    @JsonFormat(pattern ="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy",iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaSalida;

}
