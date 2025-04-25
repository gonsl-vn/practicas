package viewnext.practica4.entitiesDTOs;

import lombok.Data;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entities.Productoras;

import java.util.Set;

/**
 * Entidad Peliculas DTO.
 */
@Data // Marca esta clase como una entidad JPA
public class PeliculasDto {

    private Long idPelicula;

    private String titulo;

    private Integer anio;

    private Directores directores;

    private Productoras productoras;

    private Set<Actores> actores;

}
