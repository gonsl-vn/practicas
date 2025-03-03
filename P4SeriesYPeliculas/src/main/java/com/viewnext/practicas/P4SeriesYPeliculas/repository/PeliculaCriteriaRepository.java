package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PeliculaCriteriaRepository {
    @PersistenceContext
    private EntityManager em;

    public List<PeliculasModel> buscarPeliculasPorCriteria (
            String title,
            Integer creationYear,
            String productoraTitle,
            String directorName,
            String actorName
    ) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PeliculasModel> cq = cb.createQuery(PeliculasModel.class);
        Root<PeliculasModel> root = cq.from(PeliculasModel.class);
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"));
        }

        if(creationYear != null) {
            predicates.add(cb.equal(root.get("age"), creationYear));
        }



        Join<PeliculasModel, ActorModel> actoresJoin = root.join("actores",
                JoinType.LEFT);
        if (actorName != null && !actorName.isEmpty()) {
            predicates.add(cb.like(cb.lower(actoresJoin.get("name")),
                    "%" + actorName.toLowerCase() + "%"));
        }

        Join<PeliculasModel, DirectorModel> directorJoin = root.join("series",
                JoinType.LEFT);
        if (directorName != null && !directorName.isEmpty()) {
            predicates.add(cb.like(cb.lower(directorJoin.get("name")),
                    "%" + directorName.toLowerCase() + "%"));
        }

        Join<PeliculasModel, ProductoraModel> productoraJoin = root.join("productora",
                JoinType.LEFT);
        if(productoraTitle!=null && !productoraTitle.isEmpty()) {
            predicates.add(cb.like(cb.lower(productoraJoin.get("name")),
                    "%" + productoraTitle.toLowerCase() + "%"));
        }

        if (predicates.size() > 0) {
            cq.select(root).where(cb.or(predicates.toArray(new Predicate[predicates.size()])))
                    .orderBy(cb.asc(root.get("name")));
        } else {
            return new ArrayList<>();
        }
        return em.createQuery(cq).getResultList();
    }
}
