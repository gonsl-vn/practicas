package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.services.PeliculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria/peliculas")
public class PeliculaControllerCriteria {

    private final PeliculaService peliculaServiceCriteria;

    public PeliculaControllerCriteria(PeliculaService peliculaServiceCriteria) {
        this.peliculaServiceCriteria = peliculaServiceCriteria;
    }

    /**
     * Inserta una nueva película usando Criteria API.
     */
    @PostMapping
    public ResponseEntity<String> insertarPelicula(@RequestBody Pelicula pelicula) {
        peliculaServiceCriteria.insertarPeliculaCriteria(pelicula);
        return ResponseEntity.ok("Película insertada correctamente.");
    }

    /**
     * Obtiene la lista de todas las películas usando Criteria API.
     */
    @GetMapping
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliculaServiceCriteria.listarPeliculasCriteria());
    }

    /**
     * Busca una película por ID usando Criteria API.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> buscarPelicula(@PathVariable int id) {
        Pelicula pelicula = peliculaServiceCriteria.buscarPeliculaCriteria(id);
        return ResponseEntity.ok(pelicula);
    }

    /**
     * Actualiza una película por ID usando Criteria API.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPelicula(@PathVariable int id, @RequestBody Pelicula peliculaNueva) {
        peliculaServiceCriteria.actualizarPeliculaCriteria(id, peliculaNueva);
        return ResponseEntity.ok("Película actualizada correctamente.");
    }

    /**
     * Elimina una película por ID usando Criteria API.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPelicula(@PathVariable int id) {
        peliculaServiceCriteria.borrarPeliculaPorIdCriteria(id);
        return ResponseEntity.ok("Película eliminada correctamente.");
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Pelicula>> filtrarPeliculas(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer ano, // o un tipo apropiado, p. ej. LocalDate
            @RequestParam(required = false) String nombreDirector,
            @RequestParam(required = false) String nombreProductora) {
        List<Pelicula> peliculasFiltradas = peliculaServiceCriteria.filtrarPeliculas(titulo, ano, nombreDirector,
                nombreProductora);
        return ResponseEntity.ok(peliculasFiltradas);
    }
}
