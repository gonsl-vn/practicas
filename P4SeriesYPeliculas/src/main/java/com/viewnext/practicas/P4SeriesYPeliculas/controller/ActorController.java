package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ActorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actores")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorEntity>> findAll() {
        return ResponseEntity.ok(actorService.listarActores());
    }

    @GetMapping("/{id}/actoresPorPeliculaId")
    public ResponseEntity<List<ActorEntity>>
    encontrarActoresPorPelicula(@PathVariable Integer id) {
        List<ActorEntity> actoresPorPeli = actorService.obtenerActoresPorPelicula(id);

        return actoresPorPeli.isEmpty()?
                ResponseEntity.noContent().build():ResponseEntity.ok(actoresPorPeli);
    }

    @PostMapping("/post/{dni}")
    public ResponseEntity<ActorModel> postActor(@PathVariable String dni,
            @RequestBody ActorModel actor) {
        return ResponseEntity.ok(actorService.addActor(actor));
    }

    @PutMapping("/put/{dni}")
    public ResponseEntity<ActorModel> putActor(@PathVariable String dni,
            @RequestBody ActorModel actor) {
        return ResponseEntity.ok(actorService.modificarActor(actor));
    }
    @DeleteMapping("/delete/{dni}")
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
