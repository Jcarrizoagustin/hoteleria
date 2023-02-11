package com.example.hoteleria.mappers.reserva;

import com.example.hoteleria.dtos.habitacion.HabitacionResponseDto;
import com.example.hoteleria.dtos.reserva.ReservaCreateDto;
import com.example.hoteleria.dtos.reserva.ReservaResponseDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.entities.Reserva;
import com.example.hoteleria.exceptions.BadRequestException;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.mappers.habitacion.HabitacionMapper;
import com.example.hoteleria.repository.ClienteRepository;
import com.example.hoteleria.repository.HabitacionRepository;
import com.example.hoteleria.services.ClienteService;
import com.example.hoteleria.services.HabitacionService;
import com.example.hoteleria.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private ReservaService reservaService;

    public Reserva reservaCreateDtoToReserva(ReservaCreateDto dto){
        Reserva reserva = new Reserva();
        if(dto.getFechaSalida().compareTo(dto.getFechaIngreso()) <= 0){
            throw new BadRequestException("La fecha de salida no puede ser menor o igual a la fecha de ingreso.");
        }

        Cliente cliente = clienteService.buscarClientePorId(dto.getIdCliente());
        if(dto.getIdHabitaciones().isEmpty()){
            throw new BadRequestException("No se especifico la/s habitacion/es a reservar");
        }
        List<Habitacion> habitaciones = dto.getIdHabitaciones().stream()
                        .map(id -> habitacionService.obtenerHabitacionPorId(id))
                        .collect(Collectors.toList());
        for(Habitacion habitacion : habitaciones){
            if(habitacionService.verificarHabitacionDisponible(dto.getFechaIngreso(),dto.getFechaSalida(),habitacion)){
                reserva.agregarHabitacion(habitacion);
            }else{
                throw new ConflictException("La habitacion con id: " + habitacion.getId().toString() + " ya se encuentra reservada");
            }
        }
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        int dias = reservaService.obtenerCantidadDeDias(reserva);
        reserva.calcularPrecioTotal(dias);
        cliente.agregarReserva(reserva);
        return reserva;
    }

    public static ReservaResponseDto reservaToReservaResponseDto(Reserva reserva){
        ReservaResponseDto responseDto = new ReservaResponseDto();
        String nombreCliente = reserva.getCliente().getApellido() + " " +reserva.getCliente().getNombre();
        responseDto.setNombreCliente(nombreCliente);
        responseDto.setEmailCliente(reserva.getCliente().getEmail());
        responseDto.setId(reserva.getId());
        List<HabitacionResponseDto> habitacionesDtos = HabitacionMapper
                .habitacionToHabitacionResponseDtoList(reserva.getHabitaciones());
        responseDto.setHabitaciones(habitacionesDtos);
        responseDto.setPrecioTotal(reserva.getPrecio().toString());
        responseDto.setFechaIngreso(reserva.getFechaIngreso().toString());
        responseDto.setFechaSalida(reserva.getFechaSalida().toString());
        return responseDto;
    }

    public static List<ReservaResponseDto> reservaToReservaResponseDtoList(List<Reserva> reservas){
        List<ReservaResponseDto> responseDtos = reservas.stream()
                .map(reserva -> reservaToReservaResponseDto(reserva))
                .collect(Collectors.toList());
        return responseDtos;
    }
}
