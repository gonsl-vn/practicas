package viewnext.practica4.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * Entidad Productoras.
 */
@Entity // Marca esta clase como una entidad JPA
@Data
@Table(name = "productoras")
public class Productoras {

    @Id // Defina la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Genera el ID automáticamente
    private Long idProductora;

    @Column
    private String nombre;

    @Column
    private Integer anioFundacion;

    @OneToMany(mappedBy = "productoras") // Relación de uno a muchos con la entidad seires
    private Set<Series> series;

    @OneToMany(mappedBy = "productoras") // Relación de uno a muchos con la entidad películas
    private Set<Peliculas> peliculas;
}
