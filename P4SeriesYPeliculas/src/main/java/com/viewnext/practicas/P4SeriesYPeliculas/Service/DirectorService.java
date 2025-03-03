package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    public DirectorEntity convertirAModelo(DirectorModel directorModel) {
        List<String> peliculas= directorModel.getPeliculas().stream().map(
                p-> p.getTitle()).collect(Collectors.toList());
        List<String> series= directorModel.getSeries().stream().map(
                s-> s.getTitle()).collect(Collectors.toList());
        return new DirectorEntity(directorModel.getName(), directorModel.getSurname(),
                directorModel.getAge(), directorModel.getNationality(), peliculas, series);
    }

    public List<DirectorEntity> listarDirectores(){
        List<DirectorModel> listaDirectores= directorRepository.findAllByOrderByNameAsc();
        return listaDirectores.stream().map(d->convertirAModelo(d)).collect(Collectors.toList());
    }
    public DirectorEntity buscarDirectorPorDni(String dni){
        DirectorModel directorEncontrado = directorRepository.findByDni(dni);
        //System.out.println(directorEncontrado.getPeliculas());
        return convertirAModelo(directorEncontrado);

    }
    public DirectorModel addDirector(DirectorModel director){
        //DirectorModel directorEncontrado = directorRepository.findByDni(director.getDni());
        if(directorRepository.findByDni(director.getDni()) == null){
            return directorRepository.save(director);
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
            return directorRepository.save(director);
        }throw new ResourceNotFoundException("El director no existe, deberias crearlo");
    }
}
