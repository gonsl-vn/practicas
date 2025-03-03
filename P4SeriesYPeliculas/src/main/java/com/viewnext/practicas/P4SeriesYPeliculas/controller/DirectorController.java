package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directores")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorEntity>> listarDirectores(){
        return ResponseEntity.ok(directorService.listarDirectores());
    }
    @GetMapping("/{dni}")
    public ResponseEntity<DirectorEntity> getDirectorPorId(@PathVariable String dni){
        return ResponseEntity.ok(directorService.buscarDirectorPorDni(dni));

    }
    @PostMapping("/post/{dni}")
    public ResponseEntity<DirectorModel> postDirector(@PathVariable String dni,
            @RequestBody DirectorModel directorModel){
        return ResponseEntity.ok(directorService.addDirector(directorModel));
    }
    @PutMapping("/put/{dni}")
    public ResponseEntity<DirectorModel> putDirector(@PathVariable String dni,
            @RequestBody DirectorModel directorModel){
        return ResponseEntity.ok(directorService.editDirector(directorModel));
    }
    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<DirectorModel> deleteDirector(@PathVariable String dni){
        return ResponseEntity.ok(directorService.deleteDirector(dni));
    }
}
