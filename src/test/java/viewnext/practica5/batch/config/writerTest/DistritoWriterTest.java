package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica5.batch.config.writer.DistritoWriter;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.DistritoRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DistritoWriterTest {

    @Mock
    private DistritoRepository distritoRepository;

    @InjectMocks
    private DistritoWriter distritoWriter;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks utilizando la anotación @Mock
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testWrite() {
        // Crea dos instancias de la entidad Distrito
        Distrito distrito1 = new Distrito();
        distrito1.setId(1);
        distrito1.setNombreDistrito("Distrito 1");
        distrito1.setNumeroViviendas(100);

        Distrito distrito2 = new Distrito();
        distrito2.setId(2);
        distrito2.setNombreDistrito("Distrito 2");
        distrito2.setNumeroViviendas(200);

        // Crea una lista con los dos distritos
        List<Distrito> distritos = Arrays.asList(distrito1, distrito2);

        // Llama al método write del escritor bajo prueba
        distritoWriter.write(distritos);

        // Verifica que el método saveAll del repositorio mock se llamó exactamente una vez
        // con la lista de distritos proporcionada al método write
        verify(distritoRepository, times(1)).saveAll(distritos);

    }
}