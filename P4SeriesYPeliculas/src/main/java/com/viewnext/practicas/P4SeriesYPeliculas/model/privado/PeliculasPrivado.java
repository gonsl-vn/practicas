package com.viewnext.practicas.P4SeriesYPeliculas.model.privado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculasPrivado {
    private Integer id;
    private String title;
    private Integer creationYear;
    private String director;
    private String productora;
    private List<String> actores;
}
