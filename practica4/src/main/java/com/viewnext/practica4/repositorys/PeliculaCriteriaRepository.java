package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Pelicula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
