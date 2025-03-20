package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.models.Serie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SerieCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserta una nueva serie en la base de datos.
     */
    @Transactional
    public void insertarSerie(Serie serie) {
        entityManager.persist(serie);
    }

    /**
     * Obtiene la lista de todas las series.
     */
    public List<Serie> listarSeries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> query = cb.createQuery(Serie.class);

        Root<Serie> serie = query.from(Serie.class);
        query.select(serie);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Busca una serie por su ID.
     */
    public Serie buscarSerie(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> query = cb.createQuery(Serie.class);
        Root<Serie> serie = query.from(Serie.class);

        Predicate predicate = cb.equal(serie.get("idSerie"), id);
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Actualiza todos los datos de una serie (excepto su ID).
     */
    @Transactional
    public void actualizarSerie(int idSerie, Serie serieNueva) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Serie> update = cb.createCriteriaUpdate(Serie.class);
        Root<Serie> root = update.from(Serie.class);

        // SET TODOS LOS CAMPOS MENOS EL ID
        update.set(root.get("titulo"), serieNueva.getTitulo());
        update.set(root.get("ano"), serieNueva.getAno());
        update.set(root.get("director"), serieNueva.getDirector());
        update.set(root.get("productora"), serieNueva.getProductora());

        // WHERE ID_SERIE = idSerie
        Predicate predicate = cb.equal(root.get("idSerie"), idSerie);
        update.where(predicate);

        // Ejecutar la actualización
        entityManager.createQuery(update).executeUpdate();
    }

    /**
     * Elimina una serie por su ID.
     */
    @Transactional
    public void borrarSeriePorId(int idSerie) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Serie> delete = cb.createCriteriaDelete(Serie.class);
        Root<Serie> serie = delete.from(Serie.class);

        // WHERE ID_SERIE = idSerie
        Predicate predicate = cb.equal(serie.get("idSerie"), idSerie);
        delete.where(predicate);

        // Ejecutar la eliminación
        entityManager.createQuery(delete).executeUpdate();
    }

    public List<Serie> filtrarSeries(String titulo, String ano, String nombreDirector, String nombreProductora) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Serie> query = cb.createQuery(Serie.class);
        Root<Serie> root = query.from(Serie.class);

        List<Predicate> predicates = new ArrayList<>();

        // 1) Filtro por "titulo" si se envía
        if (titulo != null && !titulo.isBlank()) {
            // Por ejemplo, un LIKE case-insensitive
            predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
        }

        // 2) Filtro por "ano" si se envía
        //    Ajusta esto según tu definición de "Año" (LocalDate, int, etc.)
        if (ano != null && !ano.isBlank()) {
            // Si "ano" es LocalDate, necesitarías parsearlo o comparar de forma distinta.
            // Si "ano" es un String que guarda el año, puedes hacer:
            predicates.add(cb.equal(root.get("ano").as(String.class), ano));
        }

        // 3) Filtro por "nombreDirector" => necesitamos un JOIN con la entidad Director
        if (nombreDirector != null && !nombreDirector.isBlank()) {
            // Haz un JOIN con "director"
            Join<Serie, Director> directorJoin = root.join("director", JoinType.LEFT);
            // Comparamos con el campo "nombre" o "apellido", según tu entidad
            predicates.add(cb.like(cb.lower(directorJoin.get("nombre")), "%" + nombreDirector.toLowerCase() + "%"));
        }

        // 4) Filtro por "nombreProductora" => JOIN con la entidad Productora
        if (nombreProductora != null && !nombreProductora.isBlank()) {
            Join<Serie, Productora> productoraJoin = root.join("productora", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(productoraJoin.get("nombre")), "%" + nombreProductora.toLowerCase() + "%"));
        }

        // Aplicamos todos los predicados con AND
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // Ejecutamos la consulta
        return entityManager.createQuery(query).getResultList();
    }

}
