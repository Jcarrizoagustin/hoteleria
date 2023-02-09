package com.example.hoteleria.services;

import com.example.hoteleria.entities.Reserva;
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

    public Reserva obtenerReservaPorId(Long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isEmpty()){
            //TODO lanzar excepcion en lugar de retornar null
            return null;
        }
        return reserva.get();
    }

    public Reserva guardarReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerTodasLasReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        if(reservas.isEmpty()){
            //TODO lanzar excepcion en lugar de retornar null
            return null;
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
            //TODO lanzar excepcion en lugar de retornar null
            //404 not found
            return null;
        }
        return reservas;
    }



    //TODO crear metodo eliminarReservaPorId()
}
