package com.example.hoteleria.services;

import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            //TODO lanzar excepcion en lugar de retornar null
            return null;
        }
        return habitaciones;
    }

    public Habitacion obtenerHabitacionPorId(Long id){
        Optional<Habitacion> habitacion = habitacionRepository.findById(id);
        if(habitacion.isEmpty()){
            //TODO lanzar excepcion en lugar de retornar null
            return null;
        }
        return habitacion.get();
    }
}
