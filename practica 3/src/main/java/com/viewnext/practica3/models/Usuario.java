package com.viewnext.practica3.models;

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
public class Usuario {
    @Column(name = "nombre")
    private String name;
    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;

}
