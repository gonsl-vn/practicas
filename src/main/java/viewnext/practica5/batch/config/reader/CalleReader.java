package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.stereotype.Component;
import viewnext.practica5.model.Calle;

import javax.persistence.EntityManagerFactory;

/**
 * The type Calle reader.
 */
@Component
public class CalleReader {

    private final EntityManagerFactory entityManagerFactory; // crear EntityManagers

    /**
     * Instantiates a new Calle reader.
     *
     * @param entityManagerFactory
     *         the entity manager factory
     */
    public CalleReader(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Reader jpa paging item reader.
     *
     * @return the jpa paging item reader
     */
    public JpaPagingItemReader<Calle> reader() {
        // Configura un lector paginado de datos Calle desde la base de datos JPA
        return new JpaPagingItemReaderBuilder<Calle>().name("calleReader") // Nombre del lector
                .entityManagerFactory(entityManagerFactory) // Asigna la fábrica de EntityManagers
                .queryString("SELECT c FROM Calle c") // Consulta para obtener todos los objetos Calle
                .pageSize(10) // Tamaño de cada página de resultados que se leerá
                .build(); // Construye el lector
    }
}