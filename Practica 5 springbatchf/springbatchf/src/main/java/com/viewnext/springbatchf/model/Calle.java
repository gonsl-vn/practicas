package com.viewnext.springbatchf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Calle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private int primerNumTramo;
    private int ultimoNumTramo;
    private String barrio;
    private int codDistrito;
    private String nomDistrito;
}
