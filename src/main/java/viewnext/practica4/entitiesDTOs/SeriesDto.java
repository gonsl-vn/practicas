package viewnext.practica4.entitiesDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * Entidad Series DTO.
 */
@Data // Marca esta clase como una entidad JPA
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesDto {

    private Long idSeries;

    private String titulo;

    private Integer anio;

    private String nombreDirector;

    private String nombreProductora;

    private Set<String> nombresActores;

}
