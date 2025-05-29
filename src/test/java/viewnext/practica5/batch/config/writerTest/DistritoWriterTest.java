package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica5.batch.config.writer.DistritoWriter;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.DistritoRepository;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The type Distrito writer test.
 */
class DistritoWriterTest {

    @Mock
    private DistritoRepository distritoRepository;

    @InjectMocks
    private DistritoWriter distritoWriter;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test write.
     */
    @Test
    void testWrite() {
        Distrito distrito1 = new Distrito();
        distrito1.setId(1);
        distrito1.setNombreDistrito("Distrito 1");
        distrito1.setNumeroViviendas(100);

        Distrito distrito2 = new Distrito();
        distrito2.setId(2);
        distrito2.setNombreDistrito("Distrito 2");
        distrito2.setNumeroViviendas(200);

        List<Distrito> distritos = List.of(distrito1, distrito2);

        distritoWriter.write(distritos);

        verify(distritoRepository, times(1)).saveAll(distritos);
    }
}
