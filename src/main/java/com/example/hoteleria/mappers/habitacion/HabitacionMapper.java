package com.example.hoteleria.mappers.habitacion;

import com.example.hoteleria.dtos.descripcion.DescripcionCreateDto;
import com.example.hoteleria.dtos.descripcion.DescripcionResponseDto;
import com.example.hoteleria.dtos.habitacion.HabitacionCreateDto;
import com.example.hoteleria.dtos.habitacion.HabitacionResponseDto;
import com.example.hoteleria.entities.Descripcion;
import com.example.hoteleria.entities.Habitacion;
import com.example.hoteleria.mappers.descripcion.DescripcionMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class HabitacionMapper {

    public static Habitacion habitacionCreateDtoToHabitacion(HabitacionCreateDto dto){
        Habitacion habitacion = new Habitacion();
        habitacion.setCapacidad(dto.getCapacidad());
        if(!dto.getDescripciones().isEmpty()){
            for(DescripcionCreateDto create : dto.getDescripciones()){
                Descripcion descripcion = DescripcionMapper.descripcionCreateDtoToDescripcion(create);
                habitacion.agregarDescripcion(descripcion);
            }
        }
        habitacion.setPrecio(new BigDecimal(dto.getPrecio()));
        return habitacion;
    }

    public static HabitacionResponseDto habitacionToHabitacionResponseDto(Habitacion habitacion){
        HabitacionResponseDto responseDto = new HabitacionResponseDto();
        responseDto.setId(habitacion.getId());
        responseDto.setCapacidad(habitacion.getCapacidad());
        responseDto.setPrecio(habitacion.getPrecio().toString());
        if(!habitacion.getDescripciones().isEmpty()){
            List<DescripcionResponseDto> descripcionResponseDtos = habitacion.getDescripciones()
                    .stream()
                    .map(descripcion -> DescripcionMapper.descripcionToDescripcionResponseDto(descripcion))
                    .collect(Collectors.toList());
            responseDto.setDescripciones(descripcionResponseDtos);
        }
        return responseDto;
    }

    public static List<HabitacionResponseDto> habitacionToHabitacionResponseDtoList(List<Habitacion> habitaciones){
        List<HabitacionResponseDto> responseDtos = habitaciones.stream()
                .map(habitacion -> habitacionToHabitacionResponseDto(habitacion))
                .collect(Collectors.toList());
        return responseDtos;
    }

}
