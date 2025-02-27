package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ActorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRespository actorRespository;

    public List<ActorModel> listarActores(){
        return actorRespository.findAll();
    }
    public ActorModel getActorByDni(String dni){
        return actorRespository.findByDni(dni);
    }
    //public List<ActorModel> getActorsByPelicula(String pelicula){
      //  return  actorRespository.findByPeliculaTitle(pelicula);
    //}
}
