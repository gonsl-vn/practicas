package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.services.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/criteria/directores")
public class DirectorControllerCriteria {

    private final DirectorService directorServiceCriteria;

    public DirectorControllerCriteria(DirectorService directorServiceCriteria) {
        this.directorServiceCriteria = directorServiceCriteria;
    }

    /**
     * Inserta un nuevo director usando Criteria API.
     */
    @PostMapping
    public ResponseEntity<String> insertarDirector(@RequestBody Director director) {
        directorServiceCriteria.insertarDirector(director);
        return ResponseEntity.ok("Director insertado correctamente.");
    }

    /**
     * Obtiene la lista de todos los directores usando Criteria API.
     */
    @GetMapping
    public ResponseEntity<List<Director>> listarDirectores() {
        return ResponseEntity.ok(directorServiceCriteria.obtenerDirectores());
    }

    /**
     * Busca un director por ID usando Criteria API.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Director>> buscarDirector(@PathVariable int id) {
        Optional<Director> director = directorServiceCriteria.obtenerDirectorPorIdCriteria(id);
        return ResponseEntity.ok(director);
    }

    /**
     * Actualiza un director por ID usando Criteria API.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarDirector(@PathVariable int id, @RequestBody Director directorNuevo) {
        directorServiceCriteria.actualizarDirector(id, directorNuevo);
        return ResponseEntity.ok("Director actualizado correctamente.");
    }

    /**
     * Elimina un director por ID usando Criteria API.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarDirector(@PathVariable int id) {
        directorServiceCriteria.eliminarDirectorCriteria(id);
        return ResponseEntity.ok("Director eliminado correctamente.");
    }
}
