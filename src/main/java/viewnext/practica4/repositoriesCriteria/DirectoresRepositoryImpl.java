package viewnext.practica4.repositoriesCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import viewnext.practica4.entities.Directores;

import java.util.List;

/**
 * Directores Repositorio Impl.
 */
public class DirectoresRepositoryImpl implements DirectoresRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager, gestiona la persistencia

    @Override
    public List<Directores> buscarDirectoresConFiltros(String nombre, String nacionalidad, Integer edadMin,
            Integer edadMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // Obtiene el CriteriaBuilder para construir consultas
        CriteriaQuery<Directores> cq = cb.createQuery(
                Directores.class); // Crea una CriteriaQuery para la entidad Directores
        Root<Directores> root = cq.from(Directores.class); // Define la raíz de la consulta (la entidad Directores)

        Predicate predicate = cb.conjunction();
        // Crea una búsqueda para buscar directores cuyo nombre contenga la cadena proporcionada (ignorando mayúsculas/minúsculas)
        if (nombre != null && !nombre.isEmpty()) {
            predicate = cb.and(predicate, cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        // Crea una búsqueda para buscar directores con la nacionalidad proporcionada (ignorando mayúsculas/minúsculas)
        if (nacionalidad != null && !nacionalidad.isEmpty()) {
            predicate = cb.and(predicate, cb.equal(cb.lower(root.get("nacionalidad")), nacionalidad.toLowerCase()));
        }

        if (edadMin != null) {
            // Crea una búsqueda para buscar directores con una edad mayor o igual a la edad mínima
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("edad"), edadMin));
        }

        if (edadMax != null) {
            // Crea una búsqueda para buscar directores con una edad menor o igual a la edad máxima
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("edad"), edadMax));
        }
        // Combina todas las búsquedas con un "AND" lógico en la cláusula "WHERE" de la consulta
        cq.where(predicate);
        // Define el orden en que se devolverán los resultados (ascendente por nombre)
        cq.orderBy(cb.asc(root.get("nombre")));

        // Ejecuta la consulta Criteria y devuelve la lista de resultados (actores que cumplen con los filtros)
        return entityManager.createQuery(cq).getResultList();
    }
}
