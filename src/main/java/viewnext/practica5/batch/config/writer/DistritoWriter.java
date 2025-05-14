package viewnext.practica5.batch.config.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.DistritoRepository;

import java.util.List;

@Component
public class DistritoWriter implements ItemWriter<Distrito> {

    private final DistritoRepository distritoRepository; // Repositorio para interactuar con la entidad Distrito en la base de datos

    public DistritoWriter(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    @Override
    public void write(List<? extends Distrito> items) {
        // Este método se llama con un lote de objetos Distrito para ser escritos
        distritoRepository.saveAll(
                items); // Utiliza el método saveAll del repositorio para guardar todos los distritos en la base de datos
    }
}