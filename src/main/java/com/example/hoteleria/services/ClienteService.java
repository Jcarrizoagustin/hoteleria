package com.example.hoteleria.services;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Long id){
        Optional<Cliente> buscado = clienteRepository.findById(id);
        if(buscado.isEmpty()){
            //TODO lanzar excepcion en lugar de retornar null
            return null;
        }
        return buscado.get();
    }

    public void eliminarClientePorId(Long id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
        }else{
            //TODO lanzar excepcion si no existe el cliente para el id proporcionado
        }
    }

    public void eliminarCliente(Cliente cliente){
        if(cliente.getId() != null){
            eliminarClientePorId(cliente.getId());
        }else{
            //TODO lanzar excepcion si el id es nulo
        }
    }

    public List<Cliente> obtenerTodosLosClientes(){
        return clienteRepository.findAll();
    }
}
