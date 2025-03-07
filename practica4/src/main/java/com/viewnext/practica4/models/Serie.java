package com.viewnext.practica4.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Serie {

    @Id
    @Column(name = "IdSerie", unique = true, nullable = false)
    private int idSerie;

    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "Año")
    private LocalDate ano;

    @Id
    @Column(name = "IdDirector", unique = true, nullable = false)
    private int idDirector;

    @Id
    @Column(name = "IdProductora", unique = true, nullable = false)
    private int idProductora;

    @Id
    @Column(name = "IdActor", unique = true, nullable = false)
    private int idActor;

}
