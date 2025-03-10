package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorCriteriaRepository;
import com.viewnext.practica4.repositorys.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorCriteriaRepository directorCriteriaRepository;

    public DirectorService(DirectorRepository directorRepository,
            DirectorCriteriaRepository directorCriteriaRepository) {
        this.directorRepository = directorRepository;
        this.directorCriteriaRepository = directorCriteriaRepository;
    }

    public List<Director> obtenerDirectores() {
        return directorRepository.findAll();
    }

    public Optional<Director> obtenerDirectorPorNombre(String nombre) {
        return directorRepository.findByNombre(nombre);
    }

    public Optional<Director> obtenerDirectorPorId(int idDirector) {
        return directorRepository.findByIdDirector(idDirector);
    }

    public void insertarDirector(Director director) {
        directorRepository.save(director);
    }

    public void eliminarDirector(int idDirector) {
        directorRepository.delete(directorRepository.findByIdDirector(idDirector).get());
    }

    public Director actualizarDirector(int idDirector, Director directorActualizado) {
        return directorRepository.findByIdDirector(idDirector).map(directorAntiguo -> {
            directorAntiguo.setNombre(directorActualizado.getNombre());
            directorAntiguo.setApellido(directorActualizado.getApellido());
            directorAntiguo.setEdad(directorActualizado.getEdad());
            directorAntiguo.setNacionalidad(directorActualizado.getNacionalidad());
            return directorRepository.save(directorAntiguo); // Se guarda en la BD
        }).orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + idDirector));
    }

    //CRITERIA

    public List<Director> obtenerDirectoresCriteria() {
        return directorCriteriaRepository.listarDirectores();
    }

    public Optional<Director> obtenerDirectorPorIdCriteria(int idDirector) {
        return directorCriteriaRepository.buscarDirector(idDirector);
    }

    public void insertarDirectorCriteria(Director director) {
        directorCriteriaRepository.insertarDirector(director);
    }

    public void eliminarDirectorCriteria(int idDirector) {
        directorCriteriaRepository.borrarDirectorPorId(idDirector);
    }

    public void actualizarActorCriteria(int idActor, Director directorActualizado) {
        directorCriteriaRepository.actualizarDirector(idActor, directorActualizado);
    }
}
