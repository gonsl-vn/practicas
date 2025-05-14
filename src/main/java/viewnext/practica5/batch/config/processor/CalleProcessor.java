package viewnext.practica5.batch.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

@Component
public class CalleProcessor implements ItemProcessor<CalleDto, Calle> {

    @Override
    public Calle process(CalleDto dto) throws Exception {
        if (dto == null) {
            return null;
        }
        // Crea un nuevo objeto Calle para almacenar los datos procesados
        Calle calle = new Calle();
        calle.setCodigoCalle(dto.getCodigoCalle());
        calle.setTipoVia(dto.getTipoVia());
        calle.setNombreCalle(dto.getNombreCalle());
        calle.setPrimerNumTramo(dto.getPrimerNumTramo());
        calle.setUltimoNumTramo(dto.getUltimoNumTramo());
        calle.setBarrio(dto.getBarrio());
        calle.setCodigoDistrito(dto.getCodigoDistrito());
        calle.setNombreDistrito(dto.getNombreDistrito());
        return calle; // Devuelve el objeto Calle procesado
    }
}
