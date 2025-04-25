package viewnext.practica4.repositoriesCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import viewnext.practica4.entities.Peliculas;

import java.util.ArrayList;
import java.util.List;

/**
 * Peliculas Repositorio Impl.
 */
public class PeliculasRepositoryImpl implements PeliculasRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager, gestiona la persistencia

    @Override
    public List<Peliculas> buscarPeliculasConFiltros(String titulo, Integer anioMin, Integer anioMax, String productora,
            String director) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // Obtiene el CriteriaBuilder para construir consultas
        CriteriaQuery<Peliculas> cq = cb.createQuery(
                Peliculas.class); // Crea una CriteriaQuery para la entidad Peliculas
        Root<Peliculas> root = cq.from(Peliculas.class); // Define la raíz de la consulta (la entidad Peliculas)

        List<Predicate> predicates = new ArrayList<>(); // Lista para almacenar las condiciones de la búsqueda

        // Crea una búsqueda para buscar películas cuyo título contenga la cadena proporcionada (ignorando mayúsculas/minúsculas)
        if (titulo != null && !titulo.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
        }

        if (anioMin != null) {
            // Crea una búsqueda para buscar peliculas con un año mayor o igual al año mínimo
            predicates.add(cb.greaterThanOrEqualTo(root.get("anio"), anioMin));
        }

        if (anioMax != null) {
            // Crea una búsqueda para buscar películas con un año menor o igual al año máximo
            predicates.add(cb.lessThanOrEqualTo(root.get("anio"), anioMax));
        }
        if (productora != null && !productora.isEmpty()) {
            // Crea una búsqueda para buscar películas por el nombre de las productoras
            predicates.add(cb.equal(cb.lower(root.get("productoras").get("nombre")), productora.toLowerCase()));
        }

        if (director != null && !director.isEmpty()) {
            // Crea una búsqueda para buscar películas por el nombre de los directores
            predicates.add(cb.equal(cb.lower(root.get("directores").get("nombre")), director.toLowerCase()));
        }

        // Combina todas las búsquedas con un "AND" lógico en la cláusula "WHERE" de la consulta
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        // Define el orden en que se devolverán los resultados (ascendente por nombre)
        cq.orderBy(cb.asc(root.get("titulo")));

        // Ejecuta la consulta Criteria y devuelve la lista de resultados (actores que cumplen con los filtros)
        return entityManager.createQuery(cq).getResultList();
    }
}
