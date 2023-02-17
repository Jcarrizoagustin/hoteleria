package com.example.hoteleria.services;

import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.exceptions.UnauthorizedException;
import com.example.hoteleria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
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
            throw new EntityNotFoundException("No existe cliente para el id: " + id.toString());
        }
        return buscado.get();
    }

    public void eliminarClientePorId(Long id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("No existe cliente para el id: " + id.toString());
        }
    }

    public void eliminarCliente(Cliente cliente){
        if(cliente.getId() != null){
            eliminarClientePorId(cliente.getId());
        }else{
            //TODO lanzar excepcion si el id es nulo
            throw new EntityNotFoundException("El id es nulo");
        }
    }

    public List<Cliente> obtenerTodosLosClientes(){
        List<Cliente> clientes = clienteRepository.findAll();
        if(clientes.isEmpty()){
            throw new EntityNotFoundException("No existen clientes registrados");
        }
        return clientes;
    }

    public Cliente obtenerClientePorEmail(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if(cliente.isEmpty()){
            throw new EntityNotFoundException("No se encuentra el cliente con email: " + email);
        }
        return cliente.get();
    }

    public Cliente actualizarCliente(Long id, Cliente dto){
        Cliente cliente = this.buscarClientePorId(id);
        String email = cliente.getEmail();
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals(email)){
            cliente.setNombre(dto.getNombre());
            cliente.setApellido(dto.getApellido());
            cliente.setEmail(dto.getEmail());
            cliente.setTelefono(dto.getTelefono());
            cliente.setPassword(dto.getPassword());
            cliente.setRoles(dto.getRoles());
            return clienteRepository.save(cliente);
        }else{
            throw new UnauthorizedException("No tiene permitido actualizar la informacion de otro cliente");
        }

    }

}
