package com.example.hoteleria.security;

import com.example.hoteleria.entities.Cliente;
import com.example.hoteleria.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password =authentication.getCredentials().toString();
        Cliente cliente = clienteService.obtenerClientePorEmail(username);
        if(passwordEncoder.matches(password,cliente.getPassword())){
            return new UsernamePasswordAuthenticationToken(username,password,cliente.getRoles());
        }else{
            throw new BadCredentialsException("Las credenciales no son correctas");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
