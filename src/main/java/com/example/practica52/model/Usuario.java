package com.example.practica52.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    @Id
    private String dni;

    @Column(name = "nombre")
    private String name;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "codPostal")
    private Integer codPostal;
    @Column(name = "importe")
    private Double importe;
    @Column(name = "numPedido")
    private Integer numPedido;
}
