package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Productora;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductoraCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Inserta una nueva productora en la base de datos.
     */
    @Transactional
    public void insertarProductora(Productora productora) {
        entityManager.persist(productora);
    }

    /**
     * Obtiene la lista de todas las productoras.
     */
    public List<Productora> listarProductoras() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productora> query = cb.createQuery(Productora.class);

        Root<Productora> productora = query.from(Productora.class);
        query.select(productora);

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Busca una productora por su ID.
     */
    public Productora buscarProductora(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productora> query = cb.createQuery(Productora.class);
        Root<Productora> productora = query.from(Productora.class);

        Predicate predicate = cb.equal(productora.get("idProductora"), id);
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Actualiza todos los datos de una productora (excepto su ID).
     */
    @Transactional
    public void actualizarProductora(int idProductora, Productora productoraNueva) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Productora> update = cb.createCriteriaUpdate(Productora.class);
        Root<Productora> root = update.from(Productora.class);

        // SET TODOS LOS CAMPOS MENOS EL ID
        update.set(root.get("nombre"), productoraNueva.getNombre());
        update.set(root.get("anoFundacion"), productoraNueva.getAnoFundacion());

        // WHERE ID_PRODUCTORA = idProductora
        Predicate predicate = cb.equal(root.get("idProductora"), idProductora);
        update.where(predicate);

        // Ejecutar la actualización
        entityManager.createQuery(update).executeUpdate();
    }

    /**
     * Elimina una productora por su ID.
     */
    @Transactional
    public void borrarProductoraPorId(int idProductora) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Productora> delete = cb.createCriteriaDelete(Productora.class);
        Root<Productora> productora = delete.from(Productora.class);

        // WHERE ID_PRODUCTORA = idProductora
        Predicate predicate = cb.equal(productora.get("idProductora"), idProductora);
        delete.where(predicate);

        // Ejecutar la eliminación
        entityManager.createQuery(delete).executeUpdate();
    }
}
