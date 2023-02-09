package com.example.hoteleria.mappers.cliente;

import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.dtos.cliente.ClienteResponseDto;
import com.example.hoteleria.entities.Cliente;

import java.util.List;
import java.util.stream.Collectors;


public class ClienteMapper {

    public static Cliente clienteCreateDtoToCliente(ClienteCreateDto dto){
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }

    public static ClienteResponseDto clienteToClienteResponseDto(Cliente cliente){
        ClienteResponseDto responseDto = new ClienteResponseDto();
        responseDto.setNombre(cliente.getApellido() + " " + cliente.getNombre());
        responseDto.setEmail(cliente.getEmail());
        responseDto.setTelefono(cliente.getTelefono());
        responseDto.setId(cliente.getId());
        return responseDto;
    }

    public static List<ClienteResponseDto> clienteToClienteResponseDtoList(List<Cliente> listadoClientes){
        List<ClienteResponseDto> listadoDtos = listadoClientes.stream()
                .map(cliente -> clienteToClienteResponseDto(cliente))
                .collect(Collectors.toList());
        return listadoDtos;
    }
}
