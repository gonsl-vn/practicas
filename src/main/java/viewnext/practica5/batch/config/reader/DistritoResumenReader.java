package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Iterator;
import java.util.List;

@Component
public class DistritoResumenReader implements ItemReader<Distrito> {

    private final CalleRepository calleRepository; // Repositorio para acceder a la información de las Calles
    private Iterator<Object[]> data; // Iterador para recorrer los resultados de la consulta

    public DistritoResumenReader(CalleRepository calleRepository) {
        this.calleRepository = calleRepository;
    }

    @Override
    public Distrito read() {
        // Este método se llama repetidamente para leer un nuevo Distrito

        if (data == null) {
            // Si el iterador es nulo la primera vez que se llama a read(),
            // ejecuta la consulta para obtener el conteo de viviendas por distrito
            List<Object[]> resultados = calleRepository.countViviendasPorDistrito();
            if (resultados == null || resultados.isEmpty()) {
                return null; // Si no hay resultados, devuelve null para indicar el fin
            }
            data = resultados.iterator(); // Inicializa el iterador con los resultados
        }

        if (data.hasNext()) {
            // Si hay más resultados en el iterador
            Object[] fila = data.next(); // Obtiene la siguiente fila de resultados

            // Obtiene el nombre del distrito, con una seguridad para valores nulos
            String nombreDistrito = fila[0] != null ? fila[0].toString() : "DESCONOCIDO";
            // Obtiene la cantidad de viviendas, asegurándose de que sea un Long
            Long cantidad = fila[1] instanceof Long ? (Long) fila[1] : Long.valueOf(fila[1].toString());

            Distrito distrito = new Distrito(); // Crea un nuevo objeto Distrito
            distrito.setNombreDistrito(nombreDistrito); // Establece el nombre del distrito
            distrito.setNumeroViviendas(cantidad.intValue()); // Establece el número de viviendas
            return distrito; // Devuelve el objeto Distrito creado
        }

        // Si no hay más resultados en el iterador, devuelve null para indicar el fin de los items
        return null;
    }
}