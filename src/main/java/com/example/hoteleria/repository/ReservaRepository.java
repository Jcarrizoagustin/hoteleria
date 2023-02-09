package com.example.hoteleria.repository;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    //List<Reserva> findAllByCliente(Cliente cliente);
    List<Reserva> findAllByFechaIngreso(LocalDate fechaIngreso);
}
