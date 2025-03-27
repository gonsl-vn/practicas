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
public class Productora {

    @Id
    @Column(name = "IdProductora", unique = true, nullable = false)
    private int idProductora;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "AñoFundacion")
    private LocalDate anoFundacion;
}
