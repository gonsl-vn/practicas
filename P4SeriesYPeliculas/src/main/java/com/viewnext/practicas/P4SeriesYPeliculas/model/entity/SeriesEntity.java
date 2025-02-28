package com.viewnext.practicas.P4SeriesYPeliculas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesEntity {
    private String title;
    private Integer creationYear;
    private List<String> actores;
    private String director;
    private String productora;
}
