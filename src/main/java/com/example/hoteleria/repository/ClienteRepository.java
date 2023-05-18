package com.example.hoteleria.repository;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.entities.Rol;
import com.example.hoteleria.utils.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByTelefono(String telefono);


}
