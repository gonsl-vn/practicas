package viewnext.practica5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Calle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CODIGO_CALLE")
    private Integer codigoCalle;

    @Column(name = "TIPO_VIA")
    private String tipoVia;

    @Column(name = "NOMBRE_CALLE")
    private String nombreCalle;

    @Column(name = "PRIMER_NUM_TRAMO")
    private Integer primerNumTramo;

    @Column(name = "ULTIMO_NUM_TRAMO")
    private Integer ultimoNumTramo;

    @Column(name = "BARRIO")
    private String barrio;

    @Column(name = "COD_DISTRITO")
    private Integer codigoDistrito;

    @Column(name = "NOM_DISTRITO")
    private String nombreDistrito;
}
