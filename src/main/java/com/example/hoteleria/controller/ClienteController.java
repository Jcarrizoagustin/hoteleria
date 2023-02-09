package com.example.hoteleria.controller;

import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.dtos.cliente.ClienteResponseDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.mappers.cliente.ClienteMapper;
import com.example.hoteleria.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClientePorId(@PathVariable Long id){
        Cliente cliente = clienteService.buscarClientePorId(id);
        ClienteResponseDto responseDto = ClienteMapper.clienteToClienteResponseDto(cliente);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getTodosLosClientes(){
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        List<ClienteResponseDto> responseDtos = ClienteMapper.clienteToClienteResponseDtoList(clientes);
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> postCliente(@RequestBody ClienteCreateDto dto){
        Cliente cliente = ClienteMapper.clienteCreateDtoToCliente(dto);
        Cliente creado = clienteService.guardarCliente(cliente);
        ClienteResponseDto responseDto = ClienteMapper.clienteToClienteResponseDto(creado);
        return ResponseEntity.ok(responseDto);
    }
}
