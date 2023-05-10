package com.example.hoteleria.services;

import com.example.hoteleria.dtos.cliente.ClienteCreateDto;
import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.exceptions.ConflictException;
import com.example.hoteleria.exceptions.EntityNotFoundException;
import com.example.hoteleria.exceptions.UnauthorizedException;
import com.example.hoteleria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;



    private BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
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

    public Cliente actualizarCliente(Cliente dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente cliente = this.obtenerClientePorEmail(email);
        if(!encode.matches(dto.getPassword(), cliente.getPassword())){
            throw new BadCredentialsException("Contraseña incorrecta");
        }
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return clienteRepository.save(cliente);
    }

    public Cliente clienteIsLogin(String email, String password) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if(cliente.isEmpty()){
            throw new EntityNotFoundException("No existe un usuario registrado para el mail:" + email);
        }
        if(encode.matches(password,cliente.get().getPassword())){
            return cliente.get();
        }else{
            throw new ConflictException("Contraseña incorrecta");
        }

    }
}
