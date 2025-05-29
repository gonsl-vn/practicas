package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Iterator;
import java.util.List;

/**
 * The type Distrito reader.
 */
@Component
public class DistritoReader implements ItemReader<Distrito> {

    private final CalleRepository calleRepository; // Repositorio para acceder a la información de las Calles
    private Iterator<Object[]> iterator; // Iterador para recorrer los resultados de la consulta
    private long idCounter = 1; // Contador para generar IDs únicos para los distritos

    /**
     * Instantiates a new Distrito reader.
     *
     * @param calleRepository
     *         the calle repository
     */
    public DistritoReader(CalleRepository calleRepository) {
        this.calleRepository = calleRepository;
    }

    @Override
    public Distrito read() {
        // Este método se llama repetidamente para leer un nuevo Distrito

        if (iterator == null) {
            // Si el iterador es nulo en la primera vez que se llama a read(),
            // ejecuta la consulta para obtener el conteo de viviendas por distrito
            List<Object[]> resultados = calleRepository.countViviendasPorDistrito();
            iterator = resultados.iterator(); // Inicializa el iterador con los resultados
        }

        if (iterator.hasNext()) {
            // Si hay más resultados en el iterador
            Object[] fila = iterator.next(); // Obtiene la siguiente fila de resultados
            String nombreDistrito = (String) fila[0]; // El primer elemento es el nombre del distrito
            Long cantidad = (Long) fila[1]; // El segundo elemento es la cantidad de viviendas

            Distrito distrito = new Distrito(); // Crea un nuevo objeto Distrito
            distrito.setId((int) idCounter++); // Asigna un ID único y lo incrementa
            distrito.setNombreDistrito(nombreDistrito); // Establece el nombre del distrito
            distrito.setNumeroViviendas(cantidad.intValue()); // Establece el número de viviendas
            return distrito; // Devuelve el objeto Distrito creado
        }

        // Si no hay más resultados en el iterador, devuelve null para indicar el fin de los items
        return null;
    }
}