package com.viewnext.practicas.P4SeriesYPeliculas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculasEntity {
    private String title;
    private String creationYear;
    private String director;
}
