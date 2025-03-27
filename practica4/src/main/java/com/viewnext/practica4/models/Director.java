package com.viewnext.practica4.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Director {

    @Id
    @Column(name = "IdDirector", unique = true, nullable = false)
    private int idDirector;

    @Column(name = "Dni", unique = true, nullable = false)
    private String dni;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Edad")
    private int edad;

    @Column(name = "Nacionalidad")
    private String nacionalidad;
}
