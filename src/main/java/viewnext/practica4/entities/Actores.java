package viewnext.practica4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Actores.
 */
@Data
@Entity // Marca esta clase como una entidad JPA
public class Actores {

    @Id // Define la clave primaria
    @Column
    private String idActor;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private Integer edad;

    @Column
    private String nacionalidad;

    @ManyToMany(mappedBy = "actores") // Relación muchos a muchos con la entidad Series
    private Set<Series> series = new HashSet<>();

    @ManyToMany(mappedBy = "actores") // Relación muchos a muchos con la entidad Peliculas
    private Set<Peliculas> peliculas = new HashSet<>();

}
