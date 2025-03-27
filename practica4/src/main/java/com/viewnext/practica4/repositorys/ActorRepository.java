package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Optional<Actor> findByNombre(String nombre);

    Optional<Actor> findByIdActor(int idActor);

}
