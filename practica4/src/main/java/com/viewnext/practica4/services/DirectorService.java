package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
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

}
