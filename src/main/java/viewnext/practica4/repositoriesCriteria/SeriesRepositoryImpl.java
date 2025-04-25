package viewnext.practica4.repositoriesCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import viewnext.practica4.entities.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Series Repositorio Impl.
 */
@Repository
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager, gestiona la persistencia

    @Override
    public List<Series> buscarSeriesConFiltros(String titulo, Integer anioMin, Integer anioMax, String director,
            String productora) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // Obtiene el CriteriaBuilder para construir consultas
        CriteriaQuery<Series> cq = cb.createQuery(Series.class); // Crea una CriteriaQuery para la entidad Series
        Root<Series> root = cq.from(Series.class); // Define la raíz de la consulta (la entidad Series)

        List<Predicate> predicates = new ArrayList<>(); // Lista para almacenar las condiciones de la búsqueda

        if (titulo != null && !titulo.isEmpty()) {
            // Crea una búsqueda para buscar series cuyo título contenga la cadena proporcionada (ignorando mayúsculas/minúsculas)
            predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
        }

        if (anioMin != null) {
            // Crea una búsqueda para buscar series con un año mayor o igual al año mínimo
            predicates.add(cb.greaterThanOrEqualTo(root.get("anio"), anioMin));
        }

        if (anioMax != null) {
            // Crea una búsqueda para buscar series con un año menor o igual al año máximo
            predicates.add(cb.lessThanOrEqualTo(root.get("anio"), anioMax));
        }

        if (director != null && !director.isEmpty()) {
            // Crea una búsqueda para buscar series por el nombre de los directores
            predicates.add(cb.equal(cb.lower(root.get("directores").get("nombre")), director.toLowerCase()));
        }

        if (productora != null && !productora.isEmpty()) {
            // Crea una búsqueda para buscar series por el nombre de las productoras
            predicates.add(cb.equal(cb.lower(root.get("productoras").get("nombre")), productora.toLowerCase()));
        }

        // Combina todas las búsquedas con un "AND" lógico en la cláusula "WHERE" de la consulta
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        // Define el orden en que se devolverán los resultados (ascendente por nombre)
        cq.orderBy(cb.asc(root.get("titulo")));

        // Ejecuta la consulta Criteria y devuelve la lista de resultados (actores que cumplen con los filtros)
        return entityManager.createQuery(cq).getResultList();
    }
}


