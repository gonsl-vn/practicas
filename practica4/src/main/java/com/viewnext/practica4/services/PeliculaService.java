package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.repositorys.PeliculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    /**
     * Inserta una nueva película en la base de datos.
     */
    @Transactional
    public Pelicula insertarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    /**
     * Obtiene la lista de todas las películas.
     */
    public List<Pelicula> listarPeliculas() {
        return peliculaRepository.findAll();
    }

    /**
     * Busca una película por ID.
     */
    public Optional<Pelicula> buscarPelicula(int id) {
        return peliculaRepository.findById(id);
    }

    /**
     * Actualiza todos los datos de una película (excepto su ID).
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
     * Elimina una película por ID.
     */
    @Transactional
    public void borrarPeliculaPorId(int idPelicula) {
        if (peliculaRepository.existsById(idPelicula)) {
            peliculaRepository.deleteById(idPelicula);
        } else {
            throw new RuntimeException("No se puede eliminar, Pelicula con ID " + idPelicula + " no encontrada.");
        }
    }
}
