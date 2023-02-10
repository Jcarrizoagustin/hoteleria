package com.example.hoteleria.services;

import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.repository.HabitacionRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitacionService {
    @Autowired
    private HabitacionRepository habitacionRepository;

    public Habitacion guardarHabitacion(Habitacion habitacion){
        return habitacionRepository.save(habitacion);
    }

    public List<Habitacion> obtenerTodasLasHabitaciones(){
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        if(habitaciones.isEmpty()){
            throw new EntityNotFoundException("No existen habitaciones registradas.");
        }
        return habitaciones;
    }

    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaIngreso, LocalDate fechaSalida){
        List<Habitacion> todasLasHabitaciones = obtenerTodasLasHabitaciones();
        List<Habitacion> resultado = todasLasHabitaciones.stream()
                .filter(habitacion -> verificarHabitacionDisponible(fechaIngreso,fechaSalida,habitacion))
                .collect(Collectors.toList());
        if(resultado.isEmpty()){
            throw new EntityNotFoundException("No existen habitaciones disponibles para las fechas entre: " + fechaIngreso.toString() + " y: "+fechaSalida.toString());
        }
        return resultado;
    }

    private boolean verificarSiNoEstaReservada(LocalDate fechaIngreso, LocalDate fechaSalida, Reserva reserva){
        return (fechaSalida.compareTo(reserva.getFechaIngreso()) <= 0 || fechaIngreso.compareTo(reserva.getFechaSalida()) >= 0);
    }

    public boolean verificarHabitacionDisponible(LocalDate fechaIngreso, LocalDate fechaSalida, Habitacion habitacion){
        for(Reserva reserva : habitacion.getReservas()){
            if(!verificarSiNoEstaReservada(fechaIngreso,fechaSalida,reserva)){
                return false;
            }
        }
        return true;
    }

    public Habitacion obtenerHabitacionPorId(Long id){
        Optional<Habitacion> habitacion = habitacionRepository.findById(id);
        if(habitacion.isEmpty()){
            throw new EntityNotFoundException("No existe habitacion para el id: " + id.toString());
        }
        return habitacion.get();
    }

    public void eliminarHabitacionPorId(Long id){
        Optional<Habitacion> habitacion = habitacionRepository.findById(id);
        if(habitacion.isEmpty()){
            throw new EntityNotFoundException("No existe la habitacion para el id: " + id.toString());
        }
        habitacionRepository.delete(habitacion.get());
    }
}
