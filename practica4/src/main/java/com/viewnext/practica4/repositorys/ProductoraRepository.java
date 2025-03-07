package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Productora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoraRepository extends JpaRepository<Productora, Integer> {

    Optional<Productora> findByNombre(String nombre);

    Optional<Productora> findByIdProductora(int idProductora);

}
