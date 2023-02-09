package com.example.hoteleria.controller;

import com.example.hoteleria.dtos.habitacion.HabitacionCreateDto;
import com.example.hoteleria.dtos.habitacion.HabitacionResponseDto;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.mappers.habitacion.HabitacionMapper;
import com.example.hoteleria.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {
    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<HabitacionResponseDto>> getTodasLasHabitaciones(){
        List<Habitacion> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
        if(habitaciones ==null){
            return ResponseEntity.notFound().build();
        }
        List<HabitacionResponseDto> responseDtoList = HabitacionMapper.habitacionToHabitacionResponseDtoList(habitaciones);
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<HabitacionResponseDto> postHabitacion(@RequestBody HabitacionCreateDto dto){
        Habitacion habitacion = HabitacionMapper.habitacionCreateDtoToHabitacion(dto);
        Habitacion creada = habitacionService.guardarHabitacion(habitacion);
        HabitacionResponseDto responseDto = HabitacionMapper.habitacionToHabitacionResponseDto(creada);
        return ResponseEntity.ok(responseDto);
    }
}
