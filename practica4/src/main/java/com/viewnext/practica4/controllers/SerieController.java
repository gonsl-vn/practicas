package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.services.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serie")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public ResponseEntity<List<Serie>> obtenerSeries() {
        return ResponseEntity.ok(serieService.obtenerSeries());
    }

    @GetMapping("/{idSerie}")
    public ResponseEntity<Serie> obtenerSeries(@PathVariable int idSerie) {
        return ResponseEntity.ok(serieService.obtenerSeriePorId(idSerie).get());
    }

    @PostMapping
    public ResponseEntity<Serie> insertarSerie(@RequestBody Serie serie) {
        serieService.insertarSerie(serie);
        return ResponseEntity.ok(serie);
    }

    @PutMapping("/{idSerie}")
    public ResponseEntity<Serie> ActualizarSerie(@PathVariable int idSerie, @RequestBody Serie serie) {
        return ResponseEntity.ok(serieService.actualizarSerie(idSerie, serie));
    }

    @DeleteMapping("/{idSerie}")
    public ResponseEntity<Void> BorrarSerie(@PathVariable int idSerie) {
        serieService.eliminarSerie(idSerie);
        return ResponseEntity.ok().build();
    }
}
