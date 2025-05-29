package viewnext.practica5.batch.config.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

/**
 * The type Calle csv processor.
 */
@Slf4j
@Configuration
public class CalleCsvProcessor {

    /**
     * Processor csv item processor.
     *
     * @return the item processor
     */
    @Bean
    public ItemProcessor<CalleDto, Calle> processorCsv() {
        // Este procesador transforma una CalleDto a una Calle
        return dto -> {
            if (dto == null) {
                log.warn("Objeto dto es null, no se procesará.");
                return null; // Si el DTO es nulo, no se procesa nada
            }

            log.info("Procesando calle con código: {}", dto.getCodigoCalle());

            if (!"ESTE".equalsIgnoreCase(dto.getNombreDistrito())) {
                log.info("Calle filtrada por no pertenecer al distrito 'ESTE': {}", dto);
                return null; // Si el distrito no es "ESTE", se filtra la calle
            }

            if (dto.getCodigoCalle() == null || dto.getNombreCalle() == null) {
                log.error("Campos obligatorios faltantes para el procesamiento: {}", dto);
                return null; // Si faltan campos obligatorios, no se procesa
            }

            // Construye y devuelve un objeto Calle a partir del CalleDto
            return Calle.builder().codigoCalle(dto.getCodigoCalle()).tipoVia(dto.getTipoVia())
                    .nombreCalle(dto.getNombreCalle()).primerNumTramo(dto.getPrimerNumTramo())
                    .ultimoNumTramo(dto.getUltimoNumTramo()).barrio(dto.getBarrio())
                    .codigoDistrito(dto.getCodigoDistrito()).nombreDistrito(dto.getNombreDistrito()).build();
        };
    }

    /**
     * Csv completo processor item processor.
     *
     * @return the item processor
     */
    @Bean
    public ItemProcessor<CalleDto, Calle> csvCompletoProcessor() {
        // Este procesador transforma una CalleDto a una Calle sin utilizar el filtro del distrito
        return dto -> {
            if (dto.getCodigoCalle() == null || dto.getNombreCalle() == null) {
                return null; // Si faltan campos obligatorios, no se procesa
            }

            // Crea un nuevo objeto Calle y copia los datos del CalleDto
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
        };
    }
}