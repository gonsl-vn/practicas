package com.viewnext.Practica62Kafka.repository;

import com.viewnext.Practica62Kafka.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombre(String nombre);
}
