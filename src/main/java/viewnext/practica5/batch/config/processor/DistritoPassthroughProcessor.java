package viewnext.practica5.batch.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;

/**
 * The type Distrito passthrough processor.
 */
@Component
public class DistritoPassthroughProcessor implements ItemProcessor<Distrito, Distrito> {

    @Override
    public Distrito process(Distrito item) {
        // Este método procesa cada objeto Distrito leído
        // Simplemente devuelve el mismo objeto Distrito que recibe como entrada
        // Esto significa que no se realiza ninguna transformación o filtrado en este procesador
        return item;
    }
}