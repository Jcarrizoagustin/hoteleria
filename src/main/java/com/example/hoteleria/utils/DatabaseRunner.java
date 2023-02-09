package com.example.hoteleria.utils;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DatabaseRunner implements CommandLineRunner {
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public void run(String... args) throws Exception {
        if(clienteRepository.count() == 0){


        }
    }
}
