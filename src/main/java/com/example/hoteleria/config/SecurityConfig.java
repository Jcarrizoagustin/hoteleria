package com.example.hoteleria.config;

import com.example.hoteleria.security.UsernamePwdAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/habitaciones","/api/v1/habitaciones/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/clientes","/api/v1/reservas").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.PUT,"/api/v1/clientes/**").hasAnyAuthority("ADMIN","USER")
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
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }*/


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new UsernamePwdAuthenticationProvider();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(List.of("Authorization"));
        cc.setAllowedOrigins(Arrays.asList("http://localhost:5500"));
        cc.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",cc);
        return url;
    }

}
