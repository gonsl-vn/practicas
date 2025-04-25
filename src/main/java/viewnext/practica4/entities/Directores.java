package viewnext.practica4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

/**
 * Entidad Directores.
 */
@Data
@Entity // Marca esta clase como una entidad JPA
public class Directores {

    @Id // Define la clave primaria
    private String idDirector;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private Integer edad;

    @Column
    private String nacionalidad;

    @OneToMany(mappedBy = "directores") // Relación de uno a muchos con la entidad Series
    private Set<Series> series;

    @OneToMany(mappedBy = "directores") // Relación de uno a muchos con la entidad películas
    private Set<Peliculas> peliculas;

}
