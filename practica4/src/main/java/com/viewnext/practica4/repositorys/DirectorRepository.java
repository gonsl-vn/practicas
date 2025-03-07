package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    Optional<Director> findByNombre(String nombre);

    Optional<Director> findByIdDirector(int idDirector);
}
