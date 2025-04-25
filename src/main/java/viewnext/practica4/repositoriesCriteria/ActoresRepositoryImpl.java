package viewnext.practica4.repositoriesCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import viewnext.practica4.entities.Actores;

import java.util.ArrayList;
import java.util.List;

/**
 * Actores Repositorio Impl.
 */
public class ActoresRepositoryImpl implements ActoresRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager, gestiona la persistencia

    @Override
    public List<Actores> buscarActoresConFiltros(String nombre, String nacionalidad, Integer edadMin, Integer edadMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // Obtiene el CriteriaBuilder para construir consultas
        CriteriaQuery<Actores> cq = cb.createQuery(Actores.class); // Crea una CriteriaQuery para la entidad Actores
        Root<Actores> root = cq.from(Actores.class); // Define la raíz de la consulta (la entidad Actores)

        List<Predicate> predicates = new ArrayList<>(); // Lista para almacenar las condiciones de la búsqueda

        if (nombre != null && !nombre.isEmpty()) {
            // Crea una búsqueda para buscar actores cuyo nombre contenga la cadena proporcionada (ignorando mayúsculas/minúsculas)
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (nacionalidad != null && !nacionalidad.isEmpty()) {
            // Crea una búsqueda para buscar actores con la nacionalidad proporcionada (ignorando mayúsculas/minúsculas)
            predicates.add(cb.equal(cb.lower(root.get("nacionalidad")), nacionalidad.toLowerCase()));
        }

        if (edadMin != null) {
            // Crea una búsqueda para buscar actores con una edad mayor o igual a la edad mínima
            predicates.add(cb.greaterThanOrEqualTo(root.get("edad"), edadMin));
        }

        if (edadMax != null) {
            // Crea una búsqueda para buscar actores con una edad menor o igual a la edad máxima
            predicates.add(cb.lessThanOrEqualTo(root.get("edad"), edadMax));
        }

        // Combina todas las búsquedas con un "AND" lógico en la cláusula "WHERE" de la consulta
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        // Define el orden en que se devolverán los resultados (ascendente por nombre)
        cq.orderBy(cb.asc(root.get("nombre")));

        // Ejecuta la consulta Criteria y devuelve la lista de resultados (actores que cumplen con los filtros)
        return entityManager.createQuery(cq).getResultList();
    }
}