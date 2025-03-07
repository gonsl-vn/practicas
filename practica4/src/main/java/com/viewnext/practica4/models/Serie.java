package com.viewnext.practica4.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Serie {

    @Id
    @Column(name = "IdSerie", unique = true, nullable = false)
    private int idSerie;

    @Column(name = "Titulo", nullable = false)
    private String titulo;

    @Column(name = "Año", nullable = false)
    private LocalDate ano;

    @ManyToOne
    @JoinColumn(name = "IdDirector", referencedColumnName = "IdDirector")
    private Director director;

    @ManyToOne
    @JoinColumn(name = "IdProductora", referencedColumnName = "IdProductora")
    private Productora productora;

    @ManyToMany
    @JoinTable(name = "serie_actores", joinColumns = @JoinColumn(name = "idSerie"),
            inverseJoinColumns = @JoinColumn(name = "idActor"))
    private List<Actor> actores;

}
