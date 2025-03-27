package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertarActor(Actor actor) {
        entityManager.persist(actor);
    }

    public List<Actor> listarActores() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> query = cb.createQuery(Actor.class);

        Root<Actor> actor = query.from(Actor.class);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<Actor> buscarActor(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Actor> query = cb.createQuery(Actor.class);
        Root<Actor> actor = query.from(Actor.class);

        Predicate predicate = cb.equal(actor.get("IdActor"), id);
        query.where(predicate);

        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());

    }

    @Transactional
    public void actualizarActor(int idActor, Actor actorNuevo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Actor> update = cb.createCriteriaUpdate(Actor.class);
        Root<Actor> root = update.from(Actor.class);

        // SET TODOS LOS CAMPOS MENOS EL ID
        update.set(root.get("nombre"), actorNuevo.getNombre());
        update.set(root.get("apellido"), actorNuevo.getApellido());
        update.set(root.get("edad"), actorNuevo.getEdad());
        update.set(root.get("nacionalidad"), actorNuevo.getNacionalidad());

        // WHERE ID_ACTOR = idActor
        Predicate predicate = cb.equal(root.get("idActor"), idActor);
        update.where(predicate);

        // Ejecutar la actualización
        entityManager.createQuery(update).executeUpdate();
    }

    @Transactional
    public void borrarActorPorId(int idActor) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Actor> delete = cb.createCriteriaDelete(Actor.class);
        Root<Actor> actor = delete.from(Actor.class);

        // WHERE ID_ACTOR = idActor
        Predicate predicate = cb.equal(actor.get("idActor"), idActor);
        delete.where(predicate);

        // Ejecutar la eliminación
        entityManager.createQuery(delete).executeUpdate();
    }

}
