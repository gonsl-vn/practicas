package viewnext.practica5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Calle dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalleDto {

    private Integer codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private Integer primerNumTramo;
    private Integer ultimoNumTramo;
    private String barrio;
    private Integer codigoDistrito;
    private String nombreDistrito;
}
