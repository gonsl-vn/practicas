package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.services.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actores")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<Actor>> obtenerActores() {
        return ResponseEntity.ok(actorService.obtenerActores());
    }

    @GetMapping("/{idActor}")
    public ResponseEntity<Actor> obtenerActores(@PathVariable int idActor) {
        return ResponseEntity.ok(actorService.obtenerActorPorId(idActor).get());
    }

    @PostMapping
    public ResponseEntity<Actor> insertarActor(@RequestBody Actor actor) {
        actorService.insertarActor(actor);
        return ResponseEntity.ok(actor);
    }

    @PutMapping("/{idActor}")
    public ResponseEntity<Actor> ActualizarActor(@PathVariable int idActor, @RequestBody Actor actor) {
        return ResponseEntity.ok(actorService.actualizarActor(idActor, actor));
    }

    @DeleteMapping("/{idActor}")
    public ResponseEntity<Void> BorrarActor(@PathVariable int idActor) {
        actorService.eliminarActor(idActor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paginadoYordenado")
    public Page<Actor> encontrarActores(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "idActor") String orden) {
        Sort sort = Sort.by(orden).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return actorService.obtenerActores(pageable);
    }

}
