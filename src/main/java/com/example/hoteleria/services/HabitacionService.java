package com.example.hoteleria.services;

import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.exceptions.EntityNotFoundException;
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
            throw new EntityNotFoundException("No existen habitaciones registradas.");
        }
        return habitaciones;
    }

    public Habitacion obtenerHabitacionPorId(Long id){
        Optional<Habitacion> habitacion = habitacionRepository.findById(id);
        if(habitacion.isEmpty()){
            throw new EntityNotFoundException("No existe habitacion para el id: " + id.toString());
        }
        return habitacion.get();
    }
}
