package com.example.hoteleria.services;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

    public Reserva guardarReserva(LocalDate fechaIngreso,LocalDate fechaSalida,Reserva reserva){
        for(Habitacion habitacion : reserva.getHabitaciones()){
            if(habitacionService.verificarHabitacionDisponible(fechaIngreso,fechaSalida,habitacion)){
                throw new ConflictException("La habitacion con id: " + habitacion.getId().toString() + " ya se encuentra reservada");
            }
        }
        return reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerTodasLasReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        if(reservas.isEmpty()){
            throw new EntityNotFoundException("No existen reservas registradas.");
        }
        return reservas;
    }

    public int obtenerCantidadDeDias(Reserva reserva){
        return Period.between(reserva.getFechaIngreso(),
                reserva.getFechaSalida())
                .getDays();
    }

    public List<Reserva> reservasDelDia(){
        List<Reserva> reservas = reservaRepository.findAllByFechaIngreso(LocalDate.now());
        if(reservas.isEmpty()){
            throw new EntityNotFoundException("No existen reservas para hoy");
        }
        return reservas;
    }

    public void eliminarReservaPorId(Long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isEmpty()){
            throw new EntityNotFoundException("No existe reserva para el id: " + id.toString());
        }
        reservaRepository.deleteById(id);
    }


}
