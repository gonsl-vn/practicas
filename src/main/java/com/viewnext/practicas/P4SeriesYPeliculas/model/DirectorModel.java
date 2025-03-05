package com.viewnext.practicas.P4SeriesYPeliculas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "directores")
public class DirectorModel {

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

    @JsonManagedReference
    @OneToMany(mappedBy = "director")
    private List<PeliculasModel> peliculas;

    @OneToMany(mappedBy = "director")
    private List<SeriesModel> series;
}
