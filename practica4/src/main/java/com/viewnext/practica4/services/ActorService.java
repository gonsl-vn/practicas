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
    /*private WebClient apiUsuarios;*/

    public ActorService(ActorRepository actorRepository, ActorCriteriaRepository actorCriteriaRepository/*,
            WebClient.Builder webClientBuilder*/) {
        this.actorRepository = actorRepository;
        this.actorCriteriaRepository = actorCriteriaRepository;/*
        this.apiUsuarios = webClientBuilder.baseUrl("http://localhost:8080/api/usuarios").build();*/
    }

    public List<Actor> obtenerActores() {
        return actorRepository.findAll();
    }

    public Optional<Actor> obtenerActorPorId(int idActor) {
        return actorRepository.findByIdActor(idActor);
    }

    public void insertarActor(Actor actor) {

       /* Map<String, String> usuarioResponse = apiUsuarios.get().uri("/" + actor.getDni()).retrieve()
                .bodyToMono(Map.class).block();

        if (usuarioResponse.get("dni").equals(actor.getDni())) {
            actorRepository.save(actor);
        } else {
            new NoSuchElementException("No se encontro el DNI buscado, no se insertara nada");
        }*/

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

        /*Map<String, String> usuarioResponse = apiUsuarios.get().uri("/" + actor.getDni()).retrieve()
                .bodyToMono(Map.class).block();

        if (usuarioResponse.get("dni").equals(actor.getDni())) {
            actorCriteriaRepository.insertarActor(actor);
        } else {
            new NoSuchElementException("No se encontro el DNI buscado, no se insertara nada");
        }*/

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
