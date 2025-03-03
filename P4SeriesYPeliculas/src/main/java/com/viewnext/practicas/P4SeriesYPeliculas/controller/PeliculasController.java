package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.Service.PeliculasService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculasController {
    @Autowired
    private PeliculasService peliculasService;

    @GetMapping
    public ResponseEntity<List<PeliculasEntity>> listarPeliculas(){
        return ResponseEntity.ok(peliculasService.listarPeliculas());
    }
    /*
    @GetMapping("/{dni}")
    public ResponseEntity<PeliculasEntity> getPeliculasPorId(@PathVariable String dni){
        return ResponseEntity.ok(peliculasService.buscarPeliculaPorDni(dni));
    }*/
    @PostMapping("/post/{id}")
    public ResponseEntity<PeliculasModel> postDirector(@PathVariable Integer id,
            @RequestBody PeliculasModel peliculasModel){
        return ResponseEntity.ok(peliculasService.addPelicula(peliculasModel));
    }
    @PutMapping("/put/{id}")
    public ResponseEntity<PeliculasModel> putPelicula(@PathVariable Integer id,
            @RequestBody PeliculasModel peliculasModel){
        return ResponseEntity.ok(peliculasService.editPelicula(peliculasModel));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable Integer id){
        return ResponseEntity.ok(peliculasService.deletePelicula(id));
    }
}
