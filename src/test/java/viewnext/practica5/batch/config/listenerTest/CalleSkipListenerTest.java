package viewnext.practica5.batch.config.listenerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import viewnext.practica5.batch.config.listener.CalleSkipListener;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * The type Calle skip listener test.
 */
public class CalleSkipListenerTest {

    @Spy
    @InjectMocks
    private CalleSkipListener listener;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Cuando hay error en lectura se registra error.
     */
    @Test
    void cuandoHayErrorEnLectura_seRegistraError() {
        Throwable error = new RuntimeException("Error de lectura");

        doNothing().when(listener).logToFile(anyString(), any(), any());

        listener.onSkipInRead(error);

        verify(listener).logToFile(eq("LECTURA"), isNull(), eq(error));
    }

    /**
     * Cuando hay error en procesamiento se registra error con item.
     */
    @Test
    void cuandoHayErrorEnProcesamiento_seRegistraErrorConItem() {
        CalleDto item = new CalleDto();
        Throwable error = new RuntimeException("Error de procesamiento");

        doNothing().when(listener).logToFile(anyString(), any(), any());

        listener.onSkipInProcess(item, error);

        verify(listener).logToFile(eq("PROCESAMIENTO"), eq(item), eq(error));
    }

    /**
     * Cuando hay error en escritura se registra error con item.
     */
    @Test
    void cuandoHayErrorEnEscritura_seRegistraErrorConItem() {
        Calle item = new Calle();
        Throwable error = new RuntimeException("Error de escritura");

        doNothing().when(listener).logToFile(anyString(), any(), any());

        listener.onSkipInWrite(item, error);

        verify(listener).logToFile(eq("ESCRITURA"), eq(item), eq(error));
    }
}
