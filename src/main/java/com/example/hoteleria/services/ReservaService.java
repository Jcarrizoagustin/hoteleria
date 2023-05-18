package com.example.hoteleria.services;

import com.example.hoteleria.dtos.reserva.ReservaResponseDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HabitacionService habitacionService;

    public Reserva obtenerReservaPorId(Long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isEmpty()){
            throw new EntityNotFoundException("No existe reserva para el id: " + id.toString());
        }
        return reserva.get();
    }

    public Reserva guardarReserva(Reserva reserva){

        return reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerTodasLasReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        if(reservas.isEmpty()){
            throw new EntityNotFoundException("No existen reservas registradas.");
        }
        return reservas;
    }

    public long obtenerCantidadDeDias(Reserva reserva){
        return ChronoUnit.DAYS.between(reserva.getFechaIngreso(),reserva.getFechaSalida());
    }

    public List<Reserva> reservasDelDia(){
        List<Reserva> reservas = reservaRepository.findAllByFechaIngreso(LocalDate.now());
        return reservas;
    }

    public void eliminarReservaPorId(Long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isEmpty()){
            throw new EntityNotFoundException("No existe reserva para el id: " + id.toString());
        }
        reservaRepository.deleteById(id);
    }

    public boolean existeReservaParaEmail(Long id, String email){
        Reserva reserva = obtenerReservaPorId(id);
        return reserva.getCliente().getEmail().equals(email);
    }

    public List<Reserva> obtenerReservasPorClienteLogueado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente cliente = clienteService.obtenerClientePorEmail(email);
        List<Reserva> reservas = reservaRepository.findAllByCliente(cliente);
        return reservas;
    }


    public List<Reserva> obtenerReservasParaUnaFechaDada(LocalDate fecha) {
        return reservaRepository.findAllByFechaIngreso(fecha);
    }
}
