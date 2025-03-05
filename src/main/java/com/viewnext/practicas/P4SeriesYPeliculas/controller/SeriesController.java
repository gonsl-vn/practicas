package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.SeriesService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.SeriesEntity;
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
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @GetMapping
    public List<SeriesEntity> getAllSeries() {
        return seriesService.listarSeries();
    }

    @GetMapping("/buscaPorParametros")
    public ResponseEntity<Page<SeriesEntity>> pruebaBusqueda(
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

        return ResponseEntity.ok(seriesService.buscaPorParametros(title,
                creationYear, productoraTitle, directorName, actorName, pageable));
    }

    @PostMapping("/post")
    public ResponseEntity<SeriesModel> postSeries(
            @RequestBody SeriesModel series) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seriesService.addSeries(series));
    }
    @DeleteMapping("/delete/{title}")
    public ResponseEntity<SeriesModel> deleteSeries(@PathVariable String title) {
        return ResponseEntity.ok(seriesService.deleteSeries(title));
    }
    @PutMapping("/put")
    public ResponseEntity<SeriesModel> putSeries(@RequestBody SeriesModel series) {
        return ResponseEntity.ok(seriesService.editSeries(series));
    }
}
