package com.example.hoteleria.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "descripcion")
public class Descripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String detalle;

    @ManyToOne
    private Habitacion habitacion;


}
