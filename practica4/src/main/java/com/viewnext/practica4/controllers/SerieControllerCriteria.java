package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.services.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria/series")
public class SerieControllerCriteria {

    private final SerieService serieServiceCriteria;

    public SerieControllerCriteria(SerieService serieServiceCriteria) {
        this.serieServiceCriteria = serieServiceCriteria;
    }

    /**
     * Inserta una nueva serie usando Criteria API.
     */
    @PostMapping
    public ResponseEntity<String> insertarSerie(@RequestBody Serie serie) {
        serieServiceCriteria.insertarSerieCriteria(serie);
        return ResponseEntity.ok("Serie insertada correctamente.");
    }

    /**
     * Obtiene la lista de todas las series usando Criteria API.
     */
    @GetMapping
    public ResponseEntity<List<Serie>> listarSeries() {
        return ResponseEntity.ok(serieServiceCriteria.obtenerSeriesCriteria());
    }

    /**
     * Busca una serie por ID usando Criteria API.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Serie> buscarSerie(@PathVariable int id) {
        Serie serie = serieServiceCriteria.obtenerSeriePorIdCriteria(id);
        return ResponseEntity.ok(serie);
    }

    /**
     * Actualiza una serie por ID usando Criteria API.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarSerie(@PathVariable int id, @RequestBody Serie serieNueva) {
        serieServiceCriteria.actualizarSerieCriteria(id, serieNueva);
        return ResponseEntity.ok("Serie actualizada correctamente.");
    }

    /**
     * Elimina una serie por ID usando Criteria API.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarSerie(@PathVariable int id) {
        serieServiceCriteria.eliminarSerieCriteria(id);
        return ResponseEntity.ok("Serie eliminada correctamente.");
    }
}
