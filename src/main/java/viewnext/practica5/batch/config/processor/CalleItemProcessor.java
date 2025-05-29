package viewnext.practica5.batch.config.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

/**
 * The type Calle item processor.
 */
@Slf4j
@Component
public class CalleItemProcessor implements ItemProcessor<CalleDto, Calle> {

    @Override
    public Calle process(CalleDto dto) {
        // Este método procesa cada objeto CalleDto que se ha leído en el origen de los datos
        // Filtrar solo registros donde el distrito sea "ESTE"
        if (!"ESTE".equalsIgnoreCase(dto.getNombreDistrito())) {
            log.info("Registro filtrado: {}", dto);
            return null; // Si el distrito no es "ESTE", devuelve null para que el registro se filtre
        }

        // Crea un nuevo objeto Calle para almacenar los datos procesados
        Calle calle = new Calle();
        calle.setCodigoCalle(dto.getCodigoCalle());
        calle.setTipoVia(dto.getTipoVia());
        calle.setNombreCalle(dto.getNombreCalle());
        calle.setBarrio(dto.getBarrio());
        calle.setCodigoDistrito(dto.getCodigoDistrito());
        calle.setNombreDistrito(dto.getNombreDistrito());

        // Procesar el primer número de tramo y lo copia
        calle.setPrimerNumTramo(dto.getPrimerNumTramo());

        // Intenta convertir el último número de tramo a Integer
        try {
            Integer ultimoNumTramo = Integer.valueOf(dto.getUltimoNumTramo());
            calle.setUltimoNumTramo(ultimoNumTramo); // Si la conversión es exitosa, se asigna
        } catch (NumberFormatException e) {
            // Si la conversión falla
            log.error("Error al convertir ultimoNumTramo para el registro: {}", dto, e);
            calle.setUltimoNumTramo(null); // Se asigna null para indicar que hubo un error
        }

        return calle; // Devuelve el objeto Calle procesado
    }
}