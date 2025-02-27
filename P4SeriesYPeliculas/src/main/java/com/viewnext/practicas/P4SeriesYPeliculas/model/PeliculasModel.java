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
@Table(name = "peliculas")
public class PeliculasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "directorId")
    private DirectorModel director;

    @ManyToOne
    @JoinColumn(name="productoraId")
    private ProductoraModel productora;

    @ManyToMany
    @JoinTable(name = "actoresDePelicula",
            joinColumns = @JoinColumn(name = "peliculaId"),
            inverseJoinColumns = @JoinColumn(name = "actorId"))
    private List<ActorModel> actores;


}
