package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;



    public List<DirectorModel> listarDirectores(){
        return directorRepository.findAllByOrderByNameAsc();
    }
    public DirectorModel buscarDirectorPorDni(String dni){
        DirectorModel directorEncontrado = directorRepository.findByDni(dni);
        //System.out.println(directorEncontrado.getPeliculas());
        return directorRepository.findByDni(dni);

    }
    public DirectorModel addDirector(DirectorModel director){
        DirectorModel directorEncontrado = directorRepository.findByDni(director.getDni());
        if(directorEncontrado.getDni() == null){
            return directorRepository.save(directorEncontrado);
        } throw new ResourceAlreadyExistsException("El director ya existe");
    }
    public DirectorModel deleteDirector(String dni){
        DirectorModel directorEncontrado = directorRepository.findByDni(dni);
        if(directorEncontrado != null){
            directorRepository.delete(directorEncontrado);
        }throw new ResourceNotFoundException("El director no existe");
    }
    public DirectorModel editDirector(DirectorModel director){
        DirectorModel directorEncontrado = directorRepository.findByDni(director.getDni());
        if(directorEncontrado != null){
            directorRepository.delete(directorEncontrado);
            return directorRepository.save(directorEncontrado);
        }throw new ResourceNotFoundException("El director no existe, deberias crearlo");
    }
}
