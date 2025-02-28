package com.viewnext.practicas.P4SeriesYPeliculas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "series")
public class SeriesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer creationYear;

    @ManyToOne
    @JoinColumn(name = "director_dni", referencedColumnName = "dni")
    private DirectorModel director;

    @ManyToOne
    @JoinColumn(name="productora_id")
    private ProductoraModel productora;

    @ManyToMany
    @JoinTable(name = "actores_de_serie",
    joinColumns = @JoinColumn(name = "serie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_dni",
            referencedColumnName = "dni"))
    private List<ActorModel> actores;



}
