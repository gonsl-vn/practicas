package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.services.ActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/criteria/actores")
public class ActorControllerCriteria {

    private final ActorService actorService;

    public ActorControllerCriteria(ActorService actorService) {
        this.actorService = actorService;
    }

    /**
     * Inserta un nuevo actor usando Criteria API.
     */
    @PostMapping
    public ResponseEntity<String> insertarActor(@RequestBody Actor actor) {
        actorService.insertarActorCriteria(actor);
        return ResponseEntity.ok("Actor insertado correctamente.");
    }

    /**
     * Obtiene la lista de todos los actores usando Criteria API.
     */
    @GetMapping
    public ResponseEntity<List<Actor>> listarActores() {
        return ResponseEntity.ok(actorService.obtenerActoresCriteria());
    }

    /**
     * Busca un actor por ID usando Criteria API.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Actor> buscarActor(@PathVariable int id) {
        Optional<Actor> actor = actorService.obtenerActorPorIdCriteria(id);
        return ResponseEntity.ok(actor.get());
    }

    /**
     * Actualiza un actor por ID usando Criteria API.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarActor(@PathVariable int id, @RequestBody Actor actorNuevo) {
        actorService.actualizarActorCriteria(id, actorNuevo);
        return ResponseEntity.ok("Actor actualizado correctamente.");
    }

    /**
     * Elimina un actor por ID usando Criteria API.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarActor(@PathVariable int id) {
        actorService.eliminarActorCriteria(id);
        return ResponseEntity.ok("Actor eliminado correctamente.");
    }
}
