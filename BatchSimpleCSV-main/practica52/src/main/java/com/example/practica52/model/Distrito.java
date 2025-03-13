package com.example.practica52.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "distritos")
@Builder
@ToString
public class Distrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOM_DISTRITO")
    private String nombreDistrito;
    @Column(name = "NUM_VIVIENDAS")
    private Integer numeroViviendas;
}
