package com.example.hoteleria.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String telefono;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Rol> roles = new ArrayList<>();

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public void agregarReserva(Reserva reserva){
        this.reservas.add(reserva);
        reserva.setCliente(this);
    }

    public void eliminarReserva(Reserva reserva){
        this.reservas.remove(reserva);
        reserva.setCliente(null);
    }

    public void agregarRol(Rol rol){
        this.roles.add(rol);
        rol.getClientes().add(this);
    }

    public void eliminarRol(Rol rol){
        this.roles.remove(rol);
        rol.getClientes().remove(this);
    }


}
