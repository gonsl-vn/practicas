package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @GetMapping("/buscarPorParametros")
    public Page<DirectorEntity> buscarDirectorPorParametros(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer directorAge,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "asc") String orden,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Sort sort =orden.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return directorService.buscarDirectorPorParametros(name,
                surname, directorAge, nationality, peliTitle,
                serieTitle, pageable);
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
