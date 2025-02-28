package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/directores")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorModel>> listarDirectores(){
        return ResponseEntity.ok(directorService.listarDirectores());
    }
    @GetMapping("/{dni}")
    public ResponseEntity<DirectorModel> getDirectorPorId(@PathVariable String dni){
        return ResponseEntity.ok(directorService.buscarDirectorPorDni(dni));

    }
}
