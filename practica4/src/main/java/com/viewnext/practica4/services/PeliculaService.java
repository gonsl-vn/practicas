package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.repositorys.PeliculaCriteriaRepository;
import com.viewnext.practica4.repositorys.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final PeliculaCriteriaRepository peliculaCriteriaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository,
            PeliculaCriteriaRepository peliculaCriteriaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.peliculaCriteriaRepository = peliculaCriteriaRepository;
    }

    // -------------------- Métodos con JPA Repository --------------------

    /**
     * Inserta una nueva película en la base de datos utilizando JPA Repository.
     */
    @Transactional
    public Pelicula insertarPelicula(Pelicula pelicula) {
        return peliculaRepository.saveAndFlush(pelicula);
    }

    /**
     * Obtiene la lista de todas las películas utilizando JPA Repository.
     */
    public List<Pelicula> listarPeliculas() {
        return peliculaRepository.findAll();
    }

    /**
     * Busca una película por ID utilizando JPA Repository.
     */
    public Optional<Pelicula> buscarPelicula(int id) {
        return peliculaRepository.findById(id);
    }

    /**
     * Actualiza todos los datos de una película (excepto su ID) utilizando JPA Repository.
     */
    @Transactional
    public Pelicula actualizarPelicula(int idPelicula, Pelicula peliculaNueva) {
        return peliculaRepository.findById(idPelicula).map(peliculaExistente -> {
            peliculaExistente.setTitulo(peliculaNueva.getTitulo());
            peliculaExistente.setAno(peliculaNueva.getAno());
            peliculaExistente.setDirector(peliculaNueva.getDirector());
            peliculaExistente.setProductora(peliculaNueva.getProductora());
            peliculaExistente.setActores(peliculaNueva.getActores());
            return peliculaRepository.save(peliculaExistente);
        }).orElseThrow(() -> new RuntimeException("Pelicula con ID " + idPelicula + " no encontrada."));
    }

    /**
     * Elimina una película por ID utilizando JPA Repository.
     */
    @Transactional
    public void borrarPeliculaPorId(int idPelicula) {
        if (peliculaRepository.existsById(idPelicula)) {
            peliculaRepository.deleteById(idPelicula);
        } else {
            throw new RuntimeException("No se puede eliminar, Pelicula con ID " + idPelicula + " no encontrada.");
        }
    }

    // -------------------- Métodos con Criteria API --------------------

    /**
     * Inserta una nueva película en la base de datos utilizando Criteria API.
     */
    @Transactional
    public void insertarPeliculaCriteria(Pelicula pelicula) {
        peliculaCriteriaRepository.insertarPelicula(pelicula);
    }

    /**
     * Obtiene la lista de todas las películas utilizando Criteria API.
     */
    public List<Pelicula> listarPeliculasCriteria() {
        return peliculaCriteriaRepository.listarPeliculas();
    }

    /**
     * Busca una película por ID utilizando Criteria API.
     */
    public Pelicula buscarPeliculaCriteria(int id) {
        return peliculaCriteriaRepository.buscarPelicula(id);
    }

    /**
     * Actualiza todos los datos de una película (excepto su ID) utilizando Criteria API.
     */
    @Transactional
    public void actualizarPeliculaCriteria(int idPelicula, Pelicula peliculaNueva) {
        peliculaCriteriaRepository.actualizarPelicula(idPelicula, peliculaNueva);
    }

    /**
     * Elimina una película por ID utilizando Criteria API.
     */
    @Transactional
    public void borrarPeliculaPorIdCriteria(int idPelicula) {
        peliculaCriteriaRepository.borrarPeliculaPorId(idPelicula);
    }

    //Filtro

    public List<Pelicula> filtrarPeliculas(String titulo, Integer ano, String nombreDirector, String nombreProductora) {
        return peliculaCriteriaRepository.filtrarPeliculas(titulo, ano, nombreDirector, nombreProductora);
    }

    //Pageables

    public Page<Pelicula> obtenerPelicula(Pageable pageable) {
        return peliculaRepository.findAll(pageable);
    }
}
