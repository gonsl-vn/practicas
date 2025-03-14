package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorCriteriaRepository;
import com.viewnext.practica4.repositorys.ActorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorCriteriaRepository actorCriteriaRepository;

    public ActorService(ActorRepository actorRepository, ActorCriteriaRepository actorCriteriaRepository) {
        this.actorRepository = actorRepository;
        this.actorCriteriaRepository = actorCriteriaRepository;
    }

    public List<Actor> obtenerActores() {
        return actorRepository.findAll();
    }

    public Optional<Actor> obtenerActorPorId(int idActor) {
        return actorRepository.findByIdActor(idActor);
    }

    public void insertarActor(Actor actor) {
        actorRepository.save(actor);
    }

    public void eliminarActor(int idActor) {
        actorRepository.delete(actorRepository.findByIdActor(idActor).get());
    }

    public Actor actualizarActor(int idActor, Actor actorActualizado) {
        return actorRepository.findByIdActor(idActor).map(actorAntiguo -> {
            actorAntiguo.setNombre(actorActualizado.getNombre());
            actorAntiguo.setApellido(actorActualizado.getApellido());
            actorAntiguo.setEdad(actorActualizado.getEdad());
            actorAntiguo.setNacionalidad(actorActualizado.getNacionalidad());
            return actorRepository.save(actorAntiguo); // Se guarda en la BD
        }).orElseThrow(() -> new RuntimeException("Actor no encontrado con ID: " + idActor));
    }

    // CRITERIA

    public List<Actor> obtenerActoresCriteria() {
        return actorCriteriaRepository.listarActores();
    }

    public Optional<Actor> obtenerActorPorIdCriteria(int idActor) {
        return actorCriteriaRepository.buscarActor(idActor);
    }

    public void insertarActorCriteria(Actor actor) {
        actorCriteriaRepository.insertarActor(actor);
    }

    public void eliminarActorCriteria(int idActor) {
        actorCriteriaRepository.borrarActorPorId(idActor);
    }

    public void actualizarActorCriteria(int idActor, Actor actorActualizado) {
        actorCriteriaRepository.actualizarActor(idActor, actorActualizado);
    }

    //Pageables

    public Page<Actor> obtenerActores(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

}
