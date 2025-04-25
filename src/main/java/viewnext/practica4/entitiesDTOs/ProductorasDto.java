package viewnext.practica4.entitiesDTOs;

import lombok.Data;

import java.util.Set;

/**
 * Entidad Productoras DTO.
 */
@Data // Marca esta clase como una entidad JPA
public class ProductorasDto {

    private Long idProductora;

    private String nombre;

    private Integer anioFundacion;

    private Set<String> seriesTitulos;

    private Set<String> PeliculasTitulos;

}
