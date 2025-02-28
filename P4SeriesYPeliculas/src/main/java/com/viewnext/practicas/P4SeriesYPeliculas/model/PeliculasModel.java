package com.viewnext.practicas.P4SeriesYPeliculas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "peliculas")
public class PeliculasModel {

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

    //@JsonManagedReference
    @ManyToMany
    @JoinTable(name = "actores_de_pelicula",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_dni",
                    referencedColumnName = "dni"))
    private List<ActorModel> actores;


}
