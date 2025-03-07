package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> obtenerActores() {
        return actorRepository.findAll();
    }

    public Optional<Actor> obtenerActorPorNombre(String nombre) {
        return actorRepository.findByNombre(nombre);
    }

    public Optional<Actor> obtenerActorPorId(int idActor) {
        return actorRepository.findByIdActor(idActor);
    }
}
