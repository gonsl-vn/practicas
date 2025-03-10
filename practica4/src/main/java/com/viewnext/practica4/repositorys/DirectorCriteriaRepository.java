package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DirectorCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserta un nuevo director en la base de datos.
     */
    @Transactional
    public void insertarDirector(Director director) {
        entityManager.persist(director);
    }

    /**
     * Obtiene la lista de todos los directores.
     */
    public List<Director> listarDirectores() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> query = cb.createQuery(Director.class);

        Root<Director> director = query.from(Director.class);
        query.select(director);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Busca un director por su ID.
     */
    public Director buscarDirector(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Director> query = cb.createQuery(Director.class);
        Root<Director> director = query.from(Director.class);

        Predicate predicate = cb.equal(director.get("idDirector"), id);
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Actualiza todos los datos de un director (excepto su ID).
     */
    @Transactional
    public void actualizarDirector(int idDirector, Director directorNuevo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Director> update = cb.createCriteriaUpdate(Director.class);
        Root<Director> root = update.from(Director.class);

        // SET TODOS LOS CAMPOS MENOS EL ID
        update.set(root.get("nombre"), directorNuevo.getNombre());
        update.set(root.get("apellido"), directorNuevo.getApellido());
        update.set(root.get("edad"), directorNuevo.getEdad());
        update.set(root.get("nacionalidad"), directorNuevo.getNacionalidad());

        // WHERE ID_DIRECTOR = idDirector
        Predicate predicate = cb.equal(root.get("idDirector"), idDirector);
        update.where(predicate);

        // Ejecutar la actualización
        entityManager.createQuery(update).executeUpdate();
    }

    /**
     * Elimina un director por su ID.
     */
    @Transactional
    public void borrarDirectorPorId(int idDirector) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Director> delete = cb.createCriteriaDelete(Director.class);
        Root<Director> director = delete.from(Director.class);

        // WHERE ID_DIRECTOR = idDirector
        Predicate predicate = cb.equal(director.get("idDirector"), idDirector);
        delete.where(predicate);

        // Ejecutar la eliminación
        entityManager.createQuery(delete).executeUpdate();
    }
}
