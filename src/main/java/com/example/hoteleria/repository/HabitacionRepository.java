package com.example.hoteleria.repository;

import com.example.hoteleria.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository  extends JpaRepository<Habitacion,Long> {


}
