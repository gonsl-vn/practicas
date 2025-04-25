package viewnext.practica4.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * Entidad Películas.
 */
@Data
@Entity // Marca esta clase como una entidad JPA
public class Peliculas {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Genera el ID automático
    private Long idPelicula;

    @Column
    private String titulo;

    @Column
    private Integer anio;

    @ManyToOne(fetch = FetchType.EAGER) // Relación de muchos a uno con la entidad directores
    @JoinColumn(name = "id_director")
    private Directores directores;

    @ManyToOne(fetch = FetchType.EAGER) // Relación de muchos a uno con la entidad productoras
    @JoinColumn(name = "id_productora")
    private Productoras productoras;

    @ManyToMany // Relación de muchos a muchos con la entidad actores
    @JoinTable(name = "pelicula_actor", joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_actor"))
    private Set<Actores> actores;
}
