package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.clients.UserClient;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.privado.DirectorPrivado;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private DirectorCriteriaRepository directorCriteriaRepository;
    @Autowired
    private UserClient userClient;

    public DirectorEntity convertirAModelo(DirectorModel directorModel) {
        List<String> peliculas= directorModel.getPeliculas().stream().map(
                p-> p.getTitle()).collect(Collectors.toList());
        List<String> series= directorModel.getSeries().stream().map(
                s-> s.getTitle()).collect(Collectors.toList());
        return new DirectorEntity(directorModel.getName(), directorModel.getSurname(),
                directorModel.getAge(), directorModel.getNationality(), peliculas, series);
    }

    public DirectorPrivado convertirAModeloPrivado(DirectorModel directorModel) {
        List<String> peliculas= directorModel.getPeliculas().stream().map(
                p-> p.getTitle()).collect(Collectors.toList());
        List<String> series= directorModel.getSeries().stream().map(
                s-> s.getTitle()).collect(Collectors.toList());
        return new DirectorPrivado(directorModel.getDni(), directorModel.getName(), directorModel.getSurname(),
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
        if(!userClient.existeUsuario(director.getDni())){
            throw new ResourceNotFoundException("No existe el usuario" +
                    "con ese dni");
        }
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

    public Page<DirectorEntity> pruebaBusqueda(String dni, String name,
            String surname, Integer directoAge, String nationality, String peliTitle,
            String serieTitle, Pageable pageable){
        List<DirectorModel> directores = directorCriteriaRepository.buscarDirectoresPorCriteria(
                dni, name, surname, directoAge, nationality, peliTitle, serieTitle
        );
        List<DirectorEntity> directoresEnModelo = directores.stream()
                .map(d->convertirAModelo(d)).collect(Collectors.toList());

        int numDirectores= directoresEnModelo.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numDirectores);
        List<DirectorEntity> directoresPaginados = directoresEnModelo.subList(start, end);
        return new PageImpl<>(directoresPaginados, pageable, numDirectores);
    }

    public Page<DirectorPrivado> pruebaBusquedaAdmin(String dni, String name,
            String surname, Integer directoAge, String nationality, String peliTitle,
            String serieTitle, Pageable pageable){
        List<DirectorModel> directores = directorCriteriaRepository.buscarDirectoresPorCriteria(
                dni, name, surname, directoAge, nationality, peliTitle, serieTitle
        );
        List<DirectorPrivado> directoresEnPrivado = directores.stream()
                .map(d->convertirAModeloPrivado(d)).collect(Collectors.toList());

        int numDirectores= directoresEnPrivado.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numDirectores);
        List<DirectorPrivado> directoresPaginados = directoresEnPrivado.subList(start, end);
        return new PageImpl<>(directoresPaginados, pageable, numDirectores);
    }



    /*public Page<DirectorEntity> buscarDirectorPorParametros(String name,
            String surname, Integer directoAge, String nationality, String peliTitle,
            String serieTitle, Pageable pageable){
        List<DirectorModel> directores = directorCriteriaRepository
                .buscarDirectoresPorCriteria(name,
                surname, directoAge, nationality, peliTitle, serieTitle);

        List<DirectorEntity> directoresEnModelo = directores.stream()
                .map(d->convertirAModelo(d)).collect(Collectors.toList());

        int numDirectores = directoresEnModelo.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numDirectores);

        List<DirectorEntity> actoresPaginados = directoresEnModelo.subList(start, end);

        return new PageImpl<>(actoresPaginados, pageable, numDirectores);
    }*/

}
