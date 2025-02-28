package com.viewnext.practicas.P4SeriesYPeliculas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name="actores")
public class ActorModel {

    @Id
    private String dni;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String nationality;

    //@JsonBackReference
    @ManyToMany(mappedBy = "actores")
    private List<PeliculasModel> peliculas;

}
