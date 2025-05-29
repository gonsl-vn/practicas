package viewnext.practica5.batch.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * The type Calle skip listener.
 */
@Slf4j
@Component
public class CalleSkipListener implements SkipListener<CalleDto, Calle> {

    private static final String LOG_FILE = "errores.log"; // erroes.log que es donde se van a guardar los errores

    @Override
    public void onSkipInRead(Throwable t) {
        // Se ejecuta cuando ocurre un error al leer un registro
        logToFile("LECTURA", null, t); // Registra el error de lectura en el archivo
    }

    @Override
    public void onSkipInProcess(CalleDto item, Throwable t) {
        // Se ejecuta cuando ocurre un error al procesar un item (CalleDto)
        logToFile("PROCESAMIENTO", item, t); // Registra el error de procesamiento en el archivo e incluye el item
    }

    @Override
    public void onSkipInWrite(Calle item, Throwable t) {
        // Se ejecuta cuando ocurre un error al escribir un item (Calle)
        logToFile("ESCRITURA", item, t); // Registra el error de escritura en el archivo e incluye el item
    }

    public void logToFile(String fase, Object item, Throwable t) {
        // Método para escribir la información del error en el archivo indicado anteriormente
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            // Abre el archivo en modo append (para añadir al final)
            writer.printf("[%s] [%s] Error en fase %s: %s%n", LocalDateTime.now(), fase,
                    item != null ? item.getClass().getSimpleName() : "null", t.getMessage());
            // Escribe la fecha, la fase (LECTURA, PROCESAMIENTO, ESCRITURA), el tipo del item y el mensaje del error
            if (item != null) {
                writer.printf("  Item: %s%n", item);
                // Si el item jo es null escribe su representación
            }
        } catch (IOException e) {
            // Captura cualquier error al escribir en el archivo
            log.error("No se pudo escribir en el archivo de errores: {}", LOG_FILE, e);
        }
    }
}