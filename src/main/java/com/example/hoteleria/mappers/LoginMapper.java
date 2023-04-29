package com.example.hoteleria.mappers;

import com.example.hoteleria.dtos.login.LoginResponseDTO;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Rol;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    public LoginResponseDTO clienteToLoginResponseDTO(Cliente cliente){
        LoginResponseDTO response = new LoginResponseDTO();
        response.setNombre(cliente.getNombre());
        response.setApellido(cliente.getApellido());
        response.setEmail(cliente.getEmail());
        response.setTelefono(cliente.getTelefono());
        for(Rol rol : cliente.getRoles()){
            if(rol.getName().toString().equalsIgnoreCase("ADMIN")){
                response.setRole("admin");
            }
        }
        return response;
    }
}
