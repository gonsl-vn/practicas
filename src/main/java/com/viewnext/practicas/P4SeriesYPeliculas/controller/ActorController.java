package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ActorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.privado.ActorPrivado;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/user/actores")
    public ResponseEntity<List<ActorEntity>> findAll() {
        return ResponseEntity.ok(actorService.listarActores());
    }
    @GetMapping("/user/actores/buscaPorParametros")
    public ResponseEntity<Page<ActorEntity>> buscarPorParametros(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer actorAge,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);



        return ResponseEntity.ok(actorService.buscarActorPorVariosParam(name, dni,
                surname,
                actorAge, nationality, peliTitle, serieTitle, pageable));
    }

    @GetMapping("/admin/actores/buscaPorParametros")
    public ResponseEntity<Page<ActorPrivado>> buscarPorParametrosAdmin(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer actorAge,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String peliTitle,
            @RequestParam(required = false) String serieTitle,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ){
        Sort sort = order.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);



        return ResponseEntity.ok(actorService.buscarActorPorVariosParamAdmin(name, dni,
                surname,
                actorAge, nationality, peliTitle, serieTitle, pageable));
    }

    @GetMapping("/admin/actores/{id}/actoresPorPeliculaId")
    public ResponseEntity<List<ActorEntity>>
    encontrarActoresPorPelicula(@PathVariable Integer id) {
        List<ActorEntity> actoresPorPeli = actorService.obtenerActoresPorPelicula(id);

        return actoresPorPeli.isEmpty()?
                ResponseEntity.noContent().build():ResponseEntity.ok(actoresPorPeli);
    }

    @PostMapping("/admin/actores/post/{dni}")
    public ResponseEntity<ActorModel> postActor(@PathVariable String dni,
            @RequestBody ActorModel actor) {
        return ResponseEntity.ok(actorService.addActor(actor));
    }

    @PutMapping("/admin/actores/put/{dni}")
    public ResponseEntity<ActorModel> putActor(@PathVariable String dni,
            @RequestBody ActorModel actor) {
        return ResponseEntity.ok(actorService.modificarActor(actor));
    }
    @DeleteMapping("/admin/actores/delete/{dni}")
    public void deleteActor(@PathVariable String dni) {
        actorService.deleteActor(dni);
    }
   /* @GetMapping("/{id}/OrdenAscendente")
    public ResponseEntity<Page<ActorModel>> obtenerActoresPorNombreOrdenados(
            @PathVariable Integer id ,
            @RequestParam(defaultValue = "2") int pageNumber) {
        Page<ActorModel> actoresEnOrden =
                actorService.obtenerActoresPorPeliculasEnOrden(id, pageNumber);
        return new ResponseEntity<>(actoresEnOrden, HttpStatus.OK);
    }*/
}
