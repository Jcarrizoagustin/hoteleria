package com.example.hoteleria.controller;

import com.example.hoteleria.dtos.login.LoginDTO;
import com.example.hoteleria.dtos.login.LoginResponseDTO;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.mappers.LoginMapper;
import com.example.hoteleria.services.ClienteService;
import com.example.hoteleria.utils.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    @Autowired
    private ClienteService service;

    @Autowired
    private LoginMapper mapper;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Validated @RequestBody LoginDTO user){
        Cliente cliente = service.clienteIsLogin(user.getEmail(), user.getPassword());
        LoginResponseDTO dto = mapper.clienteToLoginResponseDTO(cliente);
        return ResponseEntity.ok(dto);
    }
}
