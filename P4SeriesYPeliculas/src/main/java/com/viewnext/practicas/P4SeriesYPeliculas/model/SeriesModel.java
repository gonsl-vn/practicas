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
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "directorId")
    private DirectorModel director;

    @ManyToOne
    @JoinColumn(name="productoraId")
    private ProductoraModel productora;

    @ManyToMany
    @JoinTable(name = "actoresDeSerie",
    joinColumns = @JoinColumn(name = "serieId"),
    inverseJoinColumns = @JoinColumn(name = "actorId"))
    private List<ActorModel> actores;



}
