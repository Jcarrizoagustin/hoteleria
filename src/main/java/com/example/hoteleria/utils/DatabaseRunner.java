package com.example.hoteleria.utils;


import com.example.hoteleria.entities.Rol;
import com.example.hoteleria.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRunner implements CommandLineRunner {
    @Autowired
    private RolRepository rolRepository;
    @Override
    public void run(String... args) throws Exception {
        if(rolRepository.count() == 0){
            Rol rol = new Rol();
            rol.setName(Roles.ADMIN);
            Rol rol2 = new Rol();
            rol2.setName(Roles.USER);
            rolRepository.save(rol);
            rolRepository.save(rol2);
        }
    }
}
