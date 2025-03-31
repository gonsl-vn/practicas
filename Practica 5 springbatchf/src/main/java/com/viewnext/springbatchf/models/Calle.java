package com.viewnext.springbatchf.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calle {

    @Id
    private int codigoCalle;         // CODIGO_CALLE
    private String tipoVia;          // TIPO_VIA
    private String nombreCalle;      // NOMBRE_CALLE
    private int primerNumTramo;      // PRIMER_NUM_TRAMO
    private int ultimoNumTramo;      // ULTIMO_NUM_TRAMO
    private String barrio;           // BARRIO
    private int codDistrito;         // COD_DISTRITO
    private String nomDistrito;      // NOM_DISTRITO
}
