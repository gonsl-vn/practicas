package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DirectorCriteriaRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DirectorModel> buscarDirectoresPorCriteria (
            String dni,
            String name,
            String surname,
            Integer directorAge,
            String nationality,
            String peliTitle,
            String serieTitle
    ) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DirectorModel> cq = cb.createQuery(DirectorModel.class);
        Root<DirectorModel> root = cq.from(DirectorModel.class);

        if(dni !=null && !dni.isEmpty()){
            predicates.add(cb.like(root.get("dni"), "%" + dni + "%"));
        }

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (surname != null && !surname.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("surname")), "%" + surname.toLowerCase() + "%"));
        }

        Predicate agePredicate = cb.equal(root.get("age"), directorAge);

        if (nationality != null && !nationality.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("nationality")), "%" + nationality.toLowerCase() + "%"));
        }

        Join<DirectorModel, PeliculasModel> peliculasJoin = root.join("peliculas", JoinType.LEFT);
        if (peliTitle != null && !peliTitle.isEmpty()) {
            predicates.add(cb.like(cb.lower(peliculasJoin.get("title")), "%" + peliTitle.toLowerCase() + "%"));
        }

        Join<DirectorModel, SeriesModel> seriesJoin = root.join("series", JoinType.LEFT);
        if (serieTitle != null && !serieTitle.isEmpty()) {
            predicates.add(cb.like(cb.lower(seriesJoin.get("title")), "%" + serieTitle.toLowerCase() + "%"));
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
