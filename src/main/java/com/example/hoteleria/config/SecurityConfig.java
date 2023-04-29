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
import org.springframework.security.web.authentication.logout.*;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/habitaciones","/api/v1/habitaciones/**"
                        ,"/v2/api-docs","/configuration/**","/swagger*/**","/webjars/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/clientes","/api/v1/login").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/clientes","/api/v1/reservas").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/api/v1/clientes/reservas").hasAuthority("USER")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.PUT,"/api/v1/clientes/**").hasAnyAuthority("ADMIN","USER")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/habitaciones").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/reservas").hasAuthority("USER")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.DELETE,"/api/v1/clientes/**","/api/v1/habitaciones/**").hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.DELETE,"/api/v1/reservas/**").hasAuthority("USER")
                .and()
                .logout(logout -> logout
                                .invalidateHttpSession(true)
                                .addLogoutHandler(logoutHandler())
                                .addLogoutHandler(new SecurityContextLogoutHandler())
                                .addLogoutHandler(new CookieClearingLogoutHandler("JSESSIONID"))
                                .logoutSuccessHandler(logoutSuccessHandler())
                                .deleteCookies("JSESSIONID")

                        )
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    http.authenticationProvider(authenticationProvider());
                })
                //.and()
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(List.of("Authorization","Content-Type"));
        cc.setAllowedOrigins(Arrays.asList("http://localhost:5500","http://localhost:5173"));
        cc.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        cc.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",cc);
        return url;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new HttpStatusReturningLogoutSuccessHandler();
    }
    @Bean
    public LogoutHandler logoutHandler(){
        return new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
    }
}
