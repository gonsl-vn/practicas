package com.viewnext.practicas.P4SeriesYPeliculas.controller;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.SeriesService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.SeriesEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
