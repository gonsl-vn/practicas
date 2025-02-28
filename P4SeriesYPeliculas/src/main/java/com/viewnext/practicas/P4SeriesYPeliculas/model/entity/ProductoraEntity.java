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
public class ProductoraEntity {
    private String name;
    private Integer foundedInYear;
    private List<String> peliculas;
    private List<String> series;
}
