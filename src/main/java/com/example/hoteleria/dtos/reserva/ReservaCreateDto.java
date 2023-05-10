package com.example.hoteleria.dtos.reserva;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservaCreateDto {
    //private Long idCliente;
    @Size(min = 1,message = "Se debe agregar al menos una habitacion para reservar")
    private List<@Positive(message = "Los id de habitaciones deben ser positivos") Long> idHabitaciones;
    @JsonFormat(pattern ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "La fecha de ingreso no puede ser menor al dia de hoy")
    private LocalDate fechaIngreso;
    @JsonFormat(pattern ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    @Future(message = "La fecha de salida no puede ser menor o igual al dia de hoy")
    private LocalDate fechaSalida;

}
