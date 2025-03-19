package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorCriteriaRepository;
import com.viewnext.practica4.repositorys.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorCriteriaRepository directorCriteriaRepository;
    private final WebClient.Builder webClientBuilder;
    private WebClient apiUsuarios;

    public DirectorService(DirectorRepository directorRepository, DirectorCriteriaRepository directorCriteriaRepository,
            WebClient.Builder webClientBuilder) {
        this.directorRepository = directorRepository;
        this.directorCriteriaRepository = directorCriteriaRepository;
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getApiUsuarios() {
        if (this.apiUsuarios == null) {
            this.apiUsuarios = webClientBuilder.baseUrl("http://localhost:8080/api/usuarios").build();
        }
        return this.apiUsuarios;
    }

    public List<Director> obtenerDirectores() {
        return directorRepository.findAll();
    }

    public Optional<Director> obtenerDirectorPorId(int idDirector) {
        return directorRepository.findById(idDirector);
    }

    public void insertarDirector(Director director) {

        Map<String, String> usuarioResponse = getApiUsuarios().get().uri("/" + director.getDni()).retrieve()
                .bodyToMono(Map.class).block();
        if (usuarioResponse != null) {
            if (usuarioResponse.get("dni").equals(director.getDni())) {
                directorRepository.save(director);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Director no encontrado", new Exception());
        }

    }

    public void eliminarDirector(int idDirector) {
        Optional<Director> director = directorRepository.findById(idDirector);
        director.ifPresent(directorRepository::delete);
        if (director.isEmpty()) {
            throw new NoSuchElementException("No se encontro el director buscado, no se eliminara nada de la BD");
        }
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
        Map<String, String> usuarioResponse = getApiUsuarios().get().uri("/" + director.getDni()).retrieve()
                .bodyToMono(Map.class).block();
        if (usuarioResponse != null) {
            if (usuarioResponse.get("dni").equals(director.getDni())) {
                directorCriteriaRepository.insertarDirector(director);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Director no encontrado", new Exception());
        }
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
