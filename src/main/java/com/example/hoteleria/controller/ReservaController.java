package com.example.hoteleria.controller;


import com.example.hoteleria.dtos.reserva.ReservaCreateDto;
import com.example.hoteleria.dtos.reserva.ReservaResponseDto;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.exceptions.ForbiddenException;
import com.example.hoteleria.mappers.reserva.ReservaMapper;
import com.example.hoteleria.services.ReservaService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    @Autowired
    private ReservaMapper reservaMapper;
    @Autowired
    private ReservaService reservaService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> getReservaPorId(@PathVariable Long id){
        Reserva reserva = reservaService.obtenerReservaPorId(id);
        ReservaResponseDto responseDto = ReservaMapper.reservaToReservaResponseDto(reserva);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> getTodasLasReservas(){
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        List<ReservaResponseDto> responseDtos = reservaMapper.reservaToReservaResponseDtoList(reservas);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<ReservaResponseDto>> getReservasDelDia(){
        List<Reserva> reservasDelDia = reservaService.reservasDelDia();
        if(reservasDelDia.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ReservaResponseDto> responseDtos = reservaMapper.reservaToReservaResponseDtoList(reservasDelDia);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDto> postReserva(@Valid @RequestBody ReservaCreateDto dto){
        Reserva reserva = reservaMapper.reservaCreateDtoToReserva(dto);
        if(reserva == null){
            throw new ConflictException("Conflicto al mapear la reserva.");
        }
        Reserva creada = reservaService.guardarReserva(reserva);
        ReservaResponseDto responseDto = ReservaMapper.reservaToReservaResponseDto(creada);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservaResponseDto>> getReservasPorFechaDeIngreso(@JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate fecha){
        List<Reserva> reservas = reservaService.obtenerReservasParaUnaFechaDada(fecha);
        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ReservaResponseDto> responseDtos = reservaMapper.reservaToReservaResponseDtoList(reservas);
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReserva(@PathVariable Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!reservaService.existeReservaParaEmail(id,email)){
           throw new ForbiddenException("El cliente con email: "+ email + " no es propietario de esta reserva");

        }
        reservaService.eliminarReservaPorId(id);
        return ResponseEntity.noContent().build();
    }
}
