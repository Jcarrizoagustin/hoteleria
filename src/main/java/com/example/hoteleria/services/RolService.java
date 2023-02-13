package com.example.hoteleria.services;

import com.example.hoteleria.entities.Rol;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol buscarRolPorId(Long id){
        Optional<Rol> rol = rolRepository.findById(id);
        if(rol.isEmpty()){
            throw new EntityNotFoundException("No se encuentra el rol con id: " + id.toString());
        }
        return rol.get();
    }
}
