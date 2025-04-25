package viewnext.practica4.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Series.
 */
@Entity // Marca esta clase como una entidad JPA
@Data
public class Series {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Genera el ID automáticamente
    private Long idSeries;

    @Column
    private String titulo;

    @Column
    private Integer anio;

    @ManyToOne // Relación de muchos a uno con la entidad directores
    @JoinColumn(name = "idDirectores")
    private Directores directores;

    @ManyToMany // Relación de muchos a muchos con la entidad actores
    @JoinTable(name = "serie_actor", joinColumns = @JoinColumn(name = "idSeries"),
            inverseJoinColumns = @JoinColumn(name = "idActor"))
    private Set<Actores> actores = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "idProductora") // Relación de muchos a uno con la entidad productoras
    private Productoras productoras;
}
