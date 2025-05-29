package viewnext.practica5.batch.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

/**
 * The type Distrito este filter processor.
 */
@Component // Marca esta clase como un componente de Spring
public class DistritoEsteFilterProcessor implements ItemProcessor<CalleDto, Calle> {

    @Override
    public Calle process(CalleDto dto) throws Exception {
        // Este método procesa cada objeto CalleDto leído

        if (dto == null || dto.getNombreDistrito() == null) {
            return null; // Si alguno es nulo, no se procesa
        }

        // Filtra los registros donde el nombre del distrito es "ESTE", se utiliza trim para que se ignore las mayúsculas/minúsculas y los espacios al principio y al final
        if ("ESTE".equalsIgnoreCase(dto.getNombreDistrito().trim())) {
            // Si el distrito es "ESTE", crea un nuevo objeto Calle y copia los datos del DTO
            Calle calle = new Calle();
            calle.setCodigoCalle(dto.getCodigoCalle());
            calle.setTipoVia(dto.getTipoVia());
            calle.setNombreCalle(dto.getNombreCalle());
            calle.setPrimerNumTramo(dto.getPrimerNumTramo());
            calle.setUltimoNumTramo(dto.getUltimoNumTramo());
            calle.setBarrio(dto.getBarrio());
            calle.setCodigoDistrito(dto.getCodigoDistrito());
            calle.setNombreDistrito(dto.getNombreDistrito());
            return calle; // Devuelve el objeto Calle creado
        }
        // Si el distrito no es "ESTE", devuelve null para que este registro se filtre
        return null;
    }
}