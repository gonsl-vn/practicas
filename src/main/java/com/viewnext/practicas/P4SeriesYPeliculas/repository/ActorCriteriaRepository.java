package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ActorCriteriaRepository {
    @PersistenceContext
    private EntityManager em;


    public List<ActorModel> buscarActoresPorCriteria (

            String name,
            String dni,
            String surname,
            Integer actorAge,
            String nationality,
            String peliTitle,
            String serieTitle
    ){
        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ActorModel> cq = cb.createQuery(ActorModel.class);
        Root<ActorModel> root = cq.from(ActorModel.class);

        if(name != null && !name.isEmpty()){
            predicates.add(cb.like(cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"));
        }

        if(dni!=null && !dni.isEmpty()){
            predicates.add(cb.like(root.get("dni"), "%" + dni + "%"));
        }


        if(surname != null && !surname.isEmpty()){
            predicates.add(cb.like(cb.lower(root.get("surname")),
                    "%" + surname.toLowerCase() + "%"));
        }

        Predicate agePredicate = cb.equal(root.get("age"), actorAge);
        if(nationality != null && !nationality.isEmpty()){
            predicates.add(cb.like(cb.lower(root.get("nationality")),
                    "%" + nationality.toLowerCase() + "%"));
        }


        Join<ActorModel, PeliculasModel> peliculasJoin = root.join("peliculas",
                JoinType.LEFT);
        if(peliTitle != null && !peliTitle.isEmpty()){
            predicates.add(cb.like(cb.lower(peliculasJoin.get("title")),
                    "%" + peliTitle.toLowerCase() + "%"));
        }


        Join<ActorModel, SeriesModel> seriesJoin= root.join("series",
                JoinType.LEFT);
        if(serieTitle != null && !serieTitle.isEmpty()){
            predicates.add(cb.like(cb.lower(seriesJoin.get("title")),
                    "%" + serieTitle.toLowerCase() + "%"));
        }

        if(predicates.size() > 0){
            cq.select(root).where(cb.or(predicates.toArray(new Predicate[predicates.size()])))
                    .orderBy(cb.asc(root.get("name")));
        }else{ return new ArrayList<>(); }
        return em.createQuery(cq).getResultList();
        /*
        Predicate searchPredicate = cb.or(namePredicate, surnamePredicate,
                agePredicate, nationalityPredicate,
                peliculaPredicate, seriePredicate);
        cq.select(root).where(searchPredicate).orderBy(cb.asc(root.get("name")));

        return em.createQuery(cq).getResultList();*/
    }
}
