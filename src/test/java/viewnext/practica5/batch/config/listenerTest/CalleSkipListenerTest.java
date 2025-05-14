package viewnext.practica5.batch.config.listenerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.listener.CalleSkipListener;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CalleSkipListenerTest {

    private CalleSkipListener listener; // Instancia del listener a probar

    @BeforeEach
    void setUp() {
        listener = new CalleSkipListener(); // Crea una instancia del listener
    }

    @Test
    void onSkipInRead_noLanzaErrores() {
        // Prueba que el método onSkipInRead se ejecuta sin lanzar excepciones
        Throwable error = new RuntimeException("Error de lectura"); // Simula una excepción de lectura
        assertDoesNotThrow(
                () -> listener.onSkipInRead(error)); // Asegura que la llamada al método no lanza ninguna excepción
    }

    @Test
    void onSkipInProcess_noLanzaErrores() {
        // Prueba que el método onSkipInProcess se ejecuta sin lanzar excepciones
        CalleDto calleDto = new CalleDto(); // Crea un objeto CalleDto simulado
        Throwable error = new RuntimeException("Error de procesamiento"); // Simula una excepción de procesamiento
        assertDoesNotThrow(() -> listener.onSkipInProcess(calleDto,
                error)); // Asegura que la llamada al método no lanza ninguna excepción
    }

    @Test
    void onSkipInWrite_noLanzaErrores() {
        // Prueba que el método onSkipInWrite se ejecuta sin lanzar excepciones
        Calle calle = new Calle(); // Crea un objeto Calle simulado
        Throwable error = new RuntimeException("Error de escritura"); // Simula una excepción de escritura
        assertDoesNotThrow(() -> listener.onSkipInWrite(calle,
                error)); // Asegura que la llamada al método no lanza ninguna excepción
    }
}