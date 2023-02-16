package com.example.hoteleria.config;

import com.example.hoteleria.security.UsernamePwdAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/habitaciones","/api/v1/habitaciones/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/clientes","/api/v1/reservas").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/clientes","/api/v1/habitaciones").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/reservas").hasAuthority("USER")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.DELETE,"/api/v1/clientes/**","/api/v1/habitaciones/**").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.DELETE,"/api/v1/reservas/**").hasAuthority("USER")
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new UsernamePwdAuthenticationProvider();
    }
}
