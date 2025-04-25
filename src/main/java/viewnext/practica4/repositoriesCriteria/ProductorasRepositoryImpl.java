package viewnext.practica4.repositoriesCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import viewnext.practica4.entities.Productoras;

import java.util.ArrayList;
import java.util.List;

/**
 * Productoras Repositorio Impl.
 */
public class ProductorasRepositoryImpl implements ProductorasRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager, gestiona la persistencia

    @Override
    public List<Productoras> buscarProductorasConFiltros(String nombre, Integer anioMin, Integer anioMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // Obtiene el CriteriaBuilder para construir consultas
        CriteriaQuery<Productoras> cq = cb.createQuery(
                Productoras.class); // Crea una CriteriaQuery para la entidad Productoras
        Root<Productoras> root = cq.from(Productoras.class); // Define la raíz de la consulta (la entidad Productoras)

        List<Predicate> predicates = new ArrayList<>(); // Lista para almacenar las condiciones de la búsqueda

        if (nombre != null && !nombre.isEmpty()) {
            // Crea una búsqueda para buscar productoras cuyo nombre contenga la cadena proporcionada (ignorando mayúsculas/minúsculas)
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (anioMin != null) {
            // Crea una búsqueda para buscar productoras con una edad mayor o igual a la edad mínima
            predicates.add(cb.greaterThanOrEqualTo(root.get("anioFundacion"), anioMin));
        }

        if (anioMax != null) {
            // Crea una búsqueda para buscar productoras con una edad menor o igual a la edad máxima
            predicates.add(cb.lessThanOrEqualTo(root.get("anioFundacion"), anioMax));
        }

        // Combina todas las búsquedas con un "AND" lógico en la cláusula "WHERE" de la consulta
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        // Define el orden en que se devolverán los resultados (ascendente por nombre)
        cq.orderBy(cb.asc(root.get("nombre")));

        // Ejecuta la consulta Criteria y devuelve la lista de resultados (productoras que cumplen con los filtros)
        return entityManager.createQuery(cq).getResultList();
    }
}
