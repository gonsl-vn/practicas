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
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula", unique = true, nullable = false)
    private int idPelicula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "ano", nullable = false)
    private LocalDate ano;

    // Relación con Director (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "id_director", referencedColumnName = "idDirector")
    private Director director;

    // Relación con Productora (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "id_productora", referencedColumnName = "idProductora")
    private Productora productora;

    // Relación con Actores (ManyToMany)
    @ManyToMany
    @JoinTable(name = "pelicula_actores", // Tabla intermedia para la relación ManyToMany
            joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_actor"))
    private List<Actor> actores;
}
