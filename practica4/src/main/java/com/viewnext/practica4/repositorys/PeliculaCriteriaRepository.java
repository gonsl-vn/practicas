package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.models.Productora;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PeliculaCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserta una nueva película en la base de datos.
     */
    @Transactional
    public void insertarPelicula(Pelicula pelicula) {
        entityManager.persist(pelicula);
    }

    /**
     * Obtiene la lista de todas las películas.
     */
    public List<Pelicula> listarPeliculas() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pelicula> query = cb.createQuery(Pelicula.class);

        Root<Pelicula> pelicula = query.from(Pelicula.class);
        query.select(pelicula);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Busca una película por su ID.
     */
    public Pelicula buscarPelicula(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pelicula> query = cb.createQuery(Pelicula.class);
        Root<Pelicula> pelicula = query.from(Pelicula.class);

        Predicate predicate = cb.equal(pelicula.get("idPelicula"), id);
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Actualiza todos los datos de una película (excepto su ID).
     */
    @Transactional
    public void actualizarPelicula(int idPelicula, Pelicula peliculaNueva) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Pelicula> update = cb.createCriteriaUpdate(Pelicula.class);
        Root<Pelicula> root = update.from(Pelicula.class);

        // SET TODOS LOS CAMPOS MENOS EL ID
        update.set(root.get("titulo"), peliculaNueva.getTitulo());
        update.set(root.get("ano"), peliculaNueva.getAno());
        update.set(root.get("director"), peliculaNueva.getDirector());
        update.set(root.get("productora"), peliculaNueva.getProductora());
        update.set(root.get("actores"), peliculaNueva.getActores());

        // WHERE ID_PELICULA = idPelicula
        Predicate predicate = cb.equal(root.get("idPelicula"), idPelicula);
        update.where(predicate);

        // Ejecutar la actualización
        entityManager.createQuery(update).executeUpdate();
    }

    /**
     * Elimina una película por su ID.
     */
    @Transactional
    public void borrarPeliculaPorId(int idPelicula) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Pelicula> delete = cb.createCriteriaDelete(Pelicula.class);
        Root<Pelicula> pelicula = delete.from(Pelicula.class);

        // WHERE ID_PELICULA = idPelicula
        Predicate predicate = cb.equal(pelicula.get("idPelicula"), idPelicula);
        delete.where(predicate);

        // Ejecutar la eliminación
        entityManager.createQuery(delete).executeUpdate();
    }

    public List<Pelicula> filtrarPeliculas(String titulo, Integer ano, String nombreDirector, String nombreProductora) {
        // 1) Obtener el CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pelicula> query = cb.createQuery(Pelicula.class);
        Root<Pelicula> root = query.from(Pelicula.class);

        // 2) Lista para ir acumulando los predicados
        List<Predicate> predicates = new ArrayList<>();

        // 3) Si 'titulo' no es nulo ni vacío, añadimos un predicado
        if (titulo != null && !titulo.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
        }

        // 4) Si 'ano' no es nulo, añadimos otro predicado
        if (ano != null) {
            // Asumiendo que 'ano' en la Entidad es un campo int o Year
            // Si es un LocalDate, ajusta la comparación (p.ej. extraer el year)
            predicates.add(cb.equal(root.get("ano"), ano));
        }

        // 5) Si 'nombreDirector' no es nulo, necesitamos JOIN para filtrar por el nombre del director
        if (nombreDirector != null && !nombreDirector.isBlank()) {
            Join<Pelicula, Director> joinDirector = root.join("director", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(joinDirector.get("nombre")), "%" + nombreDirector.toLowerCase() + "%"));
        }

        // 6) Si 'nombreProductora' no es nulo, hacemos un JOIN con 'productora'
        if (nombreProductora != null && !nombreProductora.isBlank()) {
            Join<Pelicula, Productora> joinProductora = root.join("productora", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(joinProductora.get("nombre")), "%" + nombreProductora.toLowerCase() + "%"));
        }

        // 7) Combinar todos los predicados con AND
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // 8) Ejecutar la consulta
        return entityManager.createQuery(query).getResultList();
    }
}
