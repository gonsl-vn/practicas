package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.privado.ActorPrivado;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ActorCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ActorRespository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculasRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    @PersistenceContext
    private EntityManager em;



    @Autowired
    private ActorRespository actorRespository;
    private PeliculasRepository peliculasRepository;
    private final ActorCriteriaRepository actorCriteriaRepository;

    public ActorEntity convertirAModelo(ActorModel actorModel) {
        List<String> peliculas = actorModel.getPeliculas().stream().
                map(p -> p.getTitle()).collect(Collectors.toList());
        List<String> series= actorModel.getSeries().stream().map(
                s-> s.getTitle()).collect(Collectors.toList());
        return new ActorEntity(actorModel.getName(),actorModel.getSurname(),
                actorModel.getAge(),actorModel.getNationality(),peliculas, series);
    }

    public ActorPrivado convertirAPrivado(ActorModel actorModel) {
        List<String> peliculas = actorModel.getPeliculas().stream().
                map(p -> p.getTitle()).collect(Collectors.toList());
        List<String> series= actorModel.getSeries().stream().map(
                s-> s.getTitle()).collect(Collectors.toList());
        return new ActorPrivado(actorModel.getDni(), actorModel.getName(),actorModel.getSurname(),
                actorModel.getAge(),actorModel.getNationality(),peliculas, series);
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

    public Page<ActorEntity> buscarActorPorVariosParam(String name,
            String dni, String surname,
            Integer age, String nationality, String peliTitle, String serieTitle,
            Pageable pageable){
        List<ActorModel> actores = actorCriteriaRepository
                .buscarActoresPorCriteria(name, dni,
                surname, age, nationality, peliTitle, serieTitle);
        List<ActorEntity> actoresEnModelo = actores.stream().map(a->convertirAModelo(a))
                .collect(Collectors.toList());
        int numActores = actoresEnModelo.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numActores);
        List<ActorEntity> actoresPaginados = actoresEnModelo.subList(start, end);
        return new PageImpl<>(actoresPaginados, pageable, numActores);
    }
    public Page<ActorPrivado> buscarActorPorVariosParamAdmin(String name,
            String dni, String surname,
            Integer age, String nationality, String peliTitle, String serieTitle,
            Pageable pageable){
        List<ActorModel> actores = actorCriteriaRepository
                .buscarActoresPorCriteria(name, dni,
                        surname, age, nationality, peliTitle, serieTitle);
        List<ActorPrivado> actoresEnPrivado = actores.stream()
                .map(a->convertirAPrivado(a)).collect(Collectors.toList());

        int numActores = actoresEnPrivado.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numActores);
        List<ActorPrivado> actoresPaginados = actoresEnPrivado.subList(start, end);
        return new PageImpl<>(actoresPaginados, pageable, numActores);
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
