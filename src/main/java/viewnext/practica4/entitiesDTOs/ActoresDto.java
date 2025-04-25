package viewnext.practica4.entitiesDTOs;

import lombok.Data;

import java.util.Set;

/**
 * Entidad Actores DTO.
 */
@Data // Marca esta clase como una entidad JPA
public class ActoresDto {

    private String idActor;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String nacionalidad;
    private Set<String> peliculasTitulos;
    private Set<String> seriesTitulos;

}
