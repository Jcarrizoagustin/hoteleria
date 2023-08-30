package com.example.hoteleria.utils;


import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.entities.Rol;
import com.example.hoteleria.repository.ClienteRepository;
import com.example.hoteleria.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseRunner implements CommandLineRunner {
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

        /*if(clienteRepository.count() == 0){
             this.newAdmin();
        }*/


    }

    private void newAdmin(){
        Rol rolAdmin = new Rol();
        rolAdmin.setName(Roles.ADMIN);
        Rol rolUser = new Rol();
        rolAdmin.setName(Roles.USER);
        Cliente cliente = new Cliente();
        //cliente.setRoles(List.of(rolAdmin,rolUser));
        cliente.agregarRol(rolAdmin);
        cliente.agregarRol(rolUser);
        cliente.setApellido("Administrador");
        cliente.setNombre("Admin");
        cliente.setEmail("admin@admin.com");
        cliente.setPassword(passwordEncoder.encode("admin"));
        cliente.setTelefono("3834112245");
        clienteRepository.save(cliente);
    }
}
