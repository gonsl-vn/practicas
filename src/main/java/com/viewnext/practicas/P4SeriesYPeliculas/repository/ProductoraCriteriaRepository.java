package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoraCriteriaRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ProductoraModel> buscarProductoraPorCriteria (
            String name,
            Integer foundedInYear,
            String peliTitle,
            String serieTitle
    ) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductoraModel> cq = cb.createQuery(ProductoraModel.class);
        Root<ProductoraModel> root = cq.from(ProductoraModel.class);
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if(foundedInYear != null){
            predicates.add(cb.equal(root.get("foundedInYear"), foundedInYear));
        }



        Join<ProductoraModel, PeliculasModel> peliculasJoin = root.join("peliculas", JoinType.LEFT);
        if (peliTitle != null && !peliTitle.isEmpty()) {
            predicates.add(cb.like(cb.lower(peliculasJoin.get("title")), "%" + peliTitle.toLowerCase() + "%"));
        }

        Join<ProductoraModel, SeriesModel> seriesJoin = root.join("series", JoinType.LEFT);
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
