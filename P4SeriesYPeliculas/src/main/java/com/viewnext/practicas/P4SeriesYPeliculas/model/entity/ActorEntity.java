package com.viewnext.practicas.P4SeriesYPeliculas.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ActorEntity {
    private String name;
    private String surname;
    private Integer age;
    private String nationality;
    private List<String> peliculas;
}
