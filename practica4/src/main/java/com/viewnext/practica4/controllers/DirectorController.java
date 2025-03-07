package com.viewnext.practica4.controllers;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.services.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directores")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<List<Director>> obtenerDirectores() {
        return ResponseEntity.ok(directorService.obtenerDirectores());
    }

    @GetMapping("/{idDirector}")
    public ResponseEntity<Director> obtenerDirectores(@PathVariable int idDirector) {
        return ResponseEntity.ok(directorService.obtenerDirectorPorId(idDirector).get());
    }

    @PostMapping
    public ResponseEntity<Director> insertarDirector(@RequestBody Director director) {
        directorService.insertarDirector(director);
        return ResponseEntity.ok(director);
    }

    @PutMapping("/{idDirector}")
    public ResponseEntity<Director> ActualizarDirector(@PathVariable int idDirector, @RequestBody Director director) {
        return ResponseEntity.ok(directorService.actualizarDirector(idDirector, director));
    }

    @DeleteMapping("/{idDirector}")
    public ResponseEntity<Void> BorrarDirector(@PathVariable int idDirector) {
        directorService.eliminarDirector(idDirector);
        return ResponseEntity.ok().build();
    }

}
