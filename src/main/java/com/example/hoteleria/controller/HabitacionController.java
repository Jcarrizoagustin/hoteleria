package com.example.hoteleria.controller;

import com.example.hoteleria.dtos.habitacion.HabitacionCreateDto;
import com.example.hoteleria.dtos.habitacion.HabitacionResponseDto;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.exceptions.BadRequestException;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.mappers.habitacion.HabitacionMapper;
import com.example.hoteleria.services.HabitacionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {


    @Autowired
    private HabitacionService habitacionService;
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<HabitacionResponseDto>> getTodasLasHabitaciones(){
        List<Habitacion> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
        List<HabitacionResponseDto> responseDtoList = HabitacionMapper.habitacionToHabitacionResponseDtoList(habitaciones);
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<HabitacionResponseDto> postHabitacion(@Valid @RequestBody HabitacionCreateDto dto){
        Habitacion habitacion = HabitacionMapper.habitacionCreateDtoToHabitacion(dto);
        Habitacion creada = habitacionService.guardarHabitacion(habitacion);
        HabitacionResponseDto responseDto = HabitacionMapper.habitacionToHabitacionResponseDto(creada);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteHabitacion(@PathVariable Long id){
        habitacionService.eliminarHabitacionPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<HabitacionResponseDto>> getHabitacionesDisponibles(@JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate fechaIngreso,
                                                                                  @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate fechaSalida){
        if(fechaSalida.compareTo(fechaIngreso) <= 0){
            throw new ConflictException("La fecha de salida no puede ser menor o igual a la fecha de entrada");
        }
        if(fechaIngreso.compareTo(LocalDate.now()) < 0 ){
            throw new BadRequestException("Las fechas no pueden ser menores al dia de hoy");
        }
        List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesDisponibles(fechaIngreso,fechaSalida);
        List<HabitacionResponseDto> responseDtos = HabitacionMapper.habitacionToHabitacionResponseDtoList(habitaciones);
        return ResponseEntity.ok(responseDtos);
    }
}
