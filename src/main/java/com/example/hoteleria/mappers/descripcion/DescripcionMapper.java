package com.example.hoteleria.mappers.descripcion;

import com.example.hoteleria.dtos.descripcion.DescripcionCreateDto;
import com.example.hoteleria.dtos.descripcion.DescripcionResponseDto;
import com.example.hoteleria.entities.Descripcion;

public class DescripcionMapper {

    public static Descripcion descripcionCreateDtoToDescripcion(DescripcionCreateDto dto){
        Descripcion descripcion = new Descripcion();
        descripcion.setDetalle(dto.getDetalle());
        return descripcion;
    }

    public static DescripcionResponseDto descripcionToDescripcionResponseDto(Descripcion descripcion){
        DescripcionResponseDto responseDto = new DescripcionResponseDto();
        responseDto.setDetalle(descripcion.getDetalle());
        return responseDto;
    }

}
