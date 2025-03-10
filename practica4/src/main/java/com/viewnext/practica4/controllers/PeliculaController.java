package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.services.PeliculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    /**
     * Obtener todas las películas
     */
    @GetMapping
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliculaService.listarPeliculas());
    }

    /**
     * Obtener una película por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> buscarPelicula(@PathVariable int id) {
        Optional<Pelicula> pelicula = peliculaService.buscarPelicula(id);
        return pelicula.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Insertar una nueva película
     */
    @PostMapping
    public ResponseEntity<Pelicula> insertarPelicula(@RequestBody Pelicula pelicula) {
        return ResponseEntity.ok(peliculaService.insertarPelicula(pelicula));
    }

    /**
     * Actualizar una película por ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable int id, @RequestBody Pelicula pelicula) {
        try {
            Pelicula peliculaActualizada = peliculaService.actualizarPelicula(id, pelicula);
            return ResponseEntity.ok(peliculaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una película por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPelicula(@PathVariable int id) {
        try {
            peliculaService.borrarPeliculaPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
