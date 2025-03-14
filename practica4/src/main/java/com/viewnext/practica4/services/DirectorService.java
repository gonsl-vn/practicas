package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorCriteriaRepository;
import com.viewnext.practica4.repositorys.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Optional<Director> obtenerDirectorPorId(int idDirector) {
        return directorRepository.findById(idDirector);
    }

    public void insertarDirector(Director director) {
        directorRepository.save(director);
    }

    public void eliminarDirector(int idDirector) {
        directorRepository.delete(directorRepository.findById(idDirector).get());
    }

    public Director actualizarDirector(int idDirector, Director directorActualizado) {
        return directorRepository.findById(idDirector).map(directorAntiguo -> {
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

    public void actualizarDirectorCriteria(int idDirector, Director actorActualizado) {
        directorCriteriaRepository.actualizarDirector(idDirector, actorActualizado);
    }

    //Pageables

    public Page<Director> obtenerDirector(Pageable pageable) {
        return directorRepository.findAll(pageable);
    }
}
