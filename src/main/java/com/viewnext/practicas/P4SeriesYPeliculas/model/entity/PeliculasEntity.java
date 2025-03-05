package com.viewnext.practicas.P4SeriesYPeliculas.model.entity;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculasEntity {
    private String title;
    private Integer creationYear;
    private String director;
    private String productora;
    private List<String> actores;
}
