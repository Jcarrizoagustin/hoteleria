package com.example.hoteleria.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacidad;

    private BigDecimal precio;

    @OneToMany(mappedBy = "habitacion",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private List<Descripcion> descripciones = new ArrayList<>();


    @ManyToMany(mappedBy = "habitaciones",cascade = CascadeType.REMOVE)
    private List<Reserva> reservas = new ArrayList<>();

    public void agregarDescripcion(Descripcion descripcion){
        this.descripciones.add(descripcion);
        descripcion.setHabitacion(this);
    }

    public void eliminarDescripcion(Descripcion descripcion){
        this.descripciones.remove(descripcion);
        descripcion.setHabitacion(null);
    }
}
