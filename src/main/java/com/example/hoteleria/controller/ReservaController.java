package com.example.hoteleria.controller;


import com.example.hoteleria.dtos.reserva.ReservaCreateDto;
import com.example.hoteleria.dtos.reserva.ReservaResponseDto;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.mappers.reserva.ReservaMapper;
import com.example.hoteleria.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if(reserva == null){
            return ResponseEntity.notFound().build();
        }
        ReservaResponseDto responseDto = ReservaMapper.reservaToReservaResponseDto(reserva);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> getTodasLasReservas(){
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        if(reservas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<ReservaResponseDto> responseDtos = ReservaMapper.reservaToReservaResponseDtoList(reservas);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDto> postReserva(@RequestBody ReservaCreateDto dto){
        Reserva reserva = reservaMapper.reservaCreateDtoToReserva(dto);
        if(reserva == null){
            return ResponseEntity.notFound().build();
        }
        Reserva creada = reservaService.guardarReserva(reserva);
        ReservaResponseDto responseDto = ReservaMapper.reservaToReservaResponseDto(creada);
        return ResponseEntity.ok(responseDto);
    }
}
