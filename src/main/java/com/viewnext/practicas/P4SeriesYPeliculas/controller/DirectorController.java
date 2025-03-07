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
@RequestMapping("/API")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/user/directores")
    public ResponseEntity<List<DirectorEntity>> listarDirectores(){
        return ResponseEntity.ok(directorService.listarDirectores());
    }
    @GetMapping("/user/directores/{dni}")
    public ResponseEntity<DirectorEntity> getDirectorPorId(@PathVariable String dni){
        return ResponseEntity.ok(directorService.buscarDirectorPorDni(dni));

    }
    @GetMapping("/user/directores/buscaPorParametros")
    public ResponseEntity<Page<DirectorEntity>> pruebaBusqueda(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer directorAge,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(directorService.pruebaBusqueda( dni, name,
                surname, directorAge, nationality, peliTitle, serieTitle, pageable));
    }
    @GetMapping("/admin/directores/buscaPorParametros")
    public ResponseEntity<Page<DirectorEntity>> pruebaBusquedaAdmin(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer directorAge,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(directorService.pruebaBusqueda( dni, name,
                surname, directorAge, nationality, peliTitle, serieTitle, pageable));
    }


   /* @GetMapping("/buscarPorParametros")
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
    }*/

    @PostMapping("/admin/directores/post/{dni}")
    public ResponseEntity<DirectorModel> postDirector(@PathVariable String dni,
            @RequestBody DirectorModel directorModel){
        return ResponseEntity.ok(directorService.addDirector(directorModel));
    }
    @PutMapping("/admin/directores/put/{dni}")
    public ResponseEntity<DirectorModel> putDirector(@PathVariable String dni,
            @RequestBody DirectorModel directorModel){
        return ResponseEntity.ok(directorService.editDirector(directorModel));
    }
    @DeleteMapping("/admin/directores/delete/{dni}")
    public ResponseEntity<String> deleteDirector(@PathVariable String dni){
        return ResponseEntity.ok(directorService.deleteDirector(dni));
    }
}
