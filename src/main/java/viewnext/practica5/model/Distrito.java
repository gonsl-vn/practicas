package viewnext.practica5.model;

import lombok.Data;

import javax.persistence.*;

/**
 * The type Distrito.
 */
@Data
@Entity
@Table(name = "distritos")
public class Distrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOM_DISTRITO")
    private String nombreDistrito;

    @Column(name = "NUM_VIVIENDAS")
    private Integer numeroViviendas;
}
