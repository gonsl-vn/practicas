package viewnext.practica5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private String nombre;
    private String dni;
    private String direccion;
    private String ciudad;
    private String codPostal;
    private Double importe;
    private Integer numPedido;
}
