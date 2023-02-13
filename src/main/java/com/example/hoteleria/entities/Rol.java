package com.example.hoteleria.entities;

import com.example.hoteleria.utils.Roles;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Rol implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles name;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.REMOVE)
    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
