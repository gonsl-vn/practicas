package com.viewnext.Practica62Kafka.repository;

import com.viewnext.Practica62Kafka.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
}
