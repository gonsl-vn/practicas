package viewnext.practica4.entitiesDTOs;

import lombok.Data;

import java.util.Set;

/**
 * Entidad Directores DTO.
 */
@Data // Marca esta clase como una entidad JPA
public class DirectoresDto {

    private String idDirector;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String nacionalidad;
    private Set<String> seriesTitulos;
    private Set<String> peliculasTitulos;

}
