package com.example.hoteleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToMany//(cascade = {CascadeType.ALL,CascadeType.MERGE})
    private List<Habitacion> habitaciones = new ArrayList<>();

    @DateTimeFormat(pattern = "dd-MM-yyyy",iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaIngreso;
    @DateTimeFormat(pattern = "dd-MM-yyyy",iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaSalida;


    private BigDecimal precio;

    public void agregarHabitacion(Habitacion habitacion){
        this.habitaciones.add(habitacion);
        habitacion.getReservas().add(this);
    }

    public void eliminarHabitacion(Habitacion habitacion){
        this.habitaciones.remove(habitacion);
        habitacion.getReservas().remove(this);
    }

    public void calcularPrecioTotal(int cantidadDeDias){
        BigDecimal total = new BigDecimal(0.00);
        for(Habitacion habitacion : this.habitaciones){
            total = total.add(habitacion.getPrecio());
        }
        this.precio = total.multiply(new BigDecimal(cantidadDeDias));
    }
}
