package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ActorRespository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculasRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ActorRespository actorRespository;
    private PeliculasRepository peliculasRepository;

    public ActorEntity convertirAModelo(ActorModel actorModel) {
        List<String> peliculas = actorModel.getPeliculas().stream().
                map(p -> p.getTitle()).collect(Collectors.toList());
        return new ActorEntity(actorModel.getName(),actorModel.getSurname(),
                actorModel.getAge(),actorModel.getNationality(),peliculas);
    }

    public List<ActorEntity> listarActores(){
        List<ActorModel> modelo = actorRespository.findAllByOrderByNameAsc();
        return modelo.stream().map( s-> convertirAModelo(s)).collect
                (Collectors.toList());
    }

    public List<ActorEntity> obtenerActoresPorPelicula(Integer peliculaId){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ActorModel> query= cb.createQuery(ActorModel.class);
        Root<ActorModel> root = query.from(ActorModel.class);

        Join<ActorModel, PeliculasModel> peliculaJoin = root.join("peliculas");
        Predicate predicate = cb.equal(peliculaJoin.get("id"), peliculaId);

        query.select(root).where(predicate).orderBy(cb.asc(root.get("name")));

        List<ActorModel> lista =  em.createQuery(query).getResultList();
        return lista.stream().map(l->convertirAModelo(l)).collect
                (Collectors.toList());
    }
    public ActorModel addActor(ActorModel actor){
        //ActorModel actorEncontrado = actorRespository.findByDni(actor.getDni());
        if(actorRespository.findByDni(actor.getDni()) == null){
          return  actorRespository.save(actor);
        }throw new ResourceAlreadyExistsException("el actor ya existe");
    }
    public void deleteActor(String dni){
        if(actorRespository.findByDni(dni)!=null){
            actorRespository.delete(actorRespository.findByDni(dni));
        } throw new ResourceNotFoundException("el actor no existe");
    }
    public ActorModel modificarActor(ActorModel actor){
        if(actorRespository.findByDni(actor.getDni())!=null){
            actorRespository.delete(actorRespository.findByDni(actor.getDni()));
            return actorRespository.save(actor);
        }throw new ResourceNotFoundException("el usuario no existe");
    }
    /*public Page<ActorModel> obtenerActoresPorPeliculasEnOrden(Integer peliculaId,
            int page){
        Pageable pageable = (Pageable) PageRequest.of(page, 10,
                Sort.by("name").ascending());
        return actorRespository.findAllByPeliculasIdOrderByNameAsc(peliculaId, pageable);
    }*/
    //public ActorModel getActorByDni(String dni){
      //  return actorRespository.findByDni(dni);
    //}
    //public List<ActorModel> getActorsByPelicula(String pelicula){
      //  return  actorRespository.findByPeliculaTitle(pelicula);
    //}
}
