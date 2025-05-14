package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.processor.DistritoPassthroughProcessor;
import viewnext.practica5.model.Distrito;

import static org.junit.jupiter.api.Assertions.*;

class DistritoPassthroughProcessorTest {

    private DistritoPassthroughProcessor processor; // Instancia del procesador passthrough a probar

    @BeforeEach
    void setUp() {
        processor = new DistritoPassthroughProcessor(); // Crea una instancia del procesador passthrough
    }

    @Test
    void devuelveElMismoObjeto() throws Exception {
        // Prueba que el procesador passthrough devuelve el mismo objeto Distrito sin modificarlo
        Distrito distrito = new Distrito();
        distrito.setNombreDistrito("CENTRO");
        distrito.setNumeroViviendas(123);

        Distrito resultado = processor.process(distrito); // Procesa el objeto Distrito

        assertSame(distrito, resultado); // Asegura que la instancia devuelta es la misma que la de entrada
        assertEquals("CENTRO", resultado.getNombreDistrito()); // Verifica que el nombre del distrito no cambió
        assertEquals(123, resultado.getNumeroViviendas()); // Verifica que el número de viviendas no cambió
    }

    @Test
    void devuelveNullSiEntradaEsNull() throws Exception {
        // Prueba que si la entrada al procesador es null, devuelve null
        Distrito resultado = processor.process(null); // Procesa un objeto nulo
        assertNull(resultado); // Asegura que el resultado es null
    }
}