package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.Service.PeliculasService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class PeliculasController {
    @Autowired
    private PeliculasService peliculasService;

    @GetMapping("/user/peliculas")
    public ResponseEntity<List<PeliculasEntity>> listarPeliculas(){
        return ResponseEntity.ok(peliculasService.listarPeliculas());
    }

    @GetMapping("/admin/peliculas/buscaPorParametros")
    public ResponseEntity<Page<PeliculasEntity>> pruebaBusqueda(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer creationYear,
            @RequestParam(required = false) String productoraTitle,
            @RequestParam(required = false) String directorName,
            @RequestParam(required = false) String actorName,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(peliculasService.buscaPorParametros(title,
                creationYear, productoraTitle, directorName, actorName, pageable));
    }

    /*
    @GetMapping("/{dni}")
    public ResponseEntity<PeliculasEntity> getPeliculasPorId(@PathVariable String dni){
        return ResponseEntity.ok(peliculasService.buscarPeliculaPorDni(dni));
    }*/
    @PostMapping("/admin/peliculas/post")
    public ResponseEntity<PeliculasModel> postPelicula(
            @RequestBody PeliculasModel peliculasModel){
        return ResponseEntity.ok(peliculasService.addPelicula(peliculasModel));
    }
    @PutMapping("/admin/peliculas/put/{title}")
    public ResponseEntity<PeliculasModel> putPelicula(@PathVariable String title,
            @RequestBody PeliculasModel peliculasModel){
        return ResponseEntity.ok(peliculasService.editPelicula(title, peliculasModel));
    }
    @DeleteMapping("/admin/peliculas/delete/{title}")
    public ResponseEntity<String> deletePelicula(@PathVariable String title){
        return ResponseEntity.ok(peliculasService.deletePelicula(title));
    }
}
