package com.example.hoteleria.mappers.cliente;

import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.dtos.cliente.ClienteResponseDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Rol;
import com.example.hoteleria.repository.RolRepository;
import com.example.hoteleria.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {
    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Cliente clienteCreateDtoToCliente(ClienteCreateDto dto){
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        List<Rol> roles = dto.getRoles()
                .stream()
                .map(id -> rolService.buscarRolPorId(id))
                .collect(Collectors.toList());
        for(Rol rol : roles){
            cliente.agregarRol(rol);
        }
        return cliente;
    }

    public ClienteResponseDto clienteToClienteResponseDto(Cliente cliente){
        ClienteResponseDto responseDto = new ClienteResponseDto();
        responseDto.setNombre(cliente.getApellido() + " " + cliente.getNombre());
        responseDto.setEmail(cliente.getEmail());
        responseDto.setTelefono(cliente.getTelefono());
        responseDto.setId(cliente.getId());
        List<String> roles = cliente.getRoles()
                .stream()
                .map(rol -> rol.getName().toString())
                .collect(Collectors.toList());
        responseDto.setRoles(roles);
        return responseDto;
    }

    public List<ClienteResponseDto> clienteToClienteResponseDtoList(List<Cliente> listadoClientes){
        List<ClienteResponseDto> listadoDtos = listadoClientes.stream()
                .map(cliente -> clienteToClienteResponseDto(cliente))
                .collect(Collectors.toList());
        return listadoDtos;
    }
}
