package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.repositorys.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public List<Serie> obtenerSeries() {
        return serieRepository.findAll();
    }

    public Optional<Serie> obtenerSeriePorTitulo(String titulo) {
        return serieRepository.findByTitulo(titulo);
    }

    public Optional<Serie> obtenerSeriePorId(int idSerie) {
        return serieRepository.findByIdSerie(idSerie);
    }

    public void insertarSerie(Serie serie) {
        serieRepository.save(serie);
    }

    public void eliminarSerie(int idSerie) {
        serieRepository.delete(serieRepository.findByIdSerie(idSerie).get());
    }

    public Serie actualizarSerie(int idSerie, Serie serieActualizada) {
        return serieRepository.findById(idSerie).map(serieAntigua -> {
            serieAntigua.setTitulo(serieActualizada.getTitulo());
            serieAntigua.setAno(serieActualizada.getAno());
            serieAntigua.setDirector(serieActualizada.getDirector());
            serieAntigua.setProductora(serieActualizada.getProductora());
            serieAntigua.setActores(serieActualizada.getActores()); // Lista de actores actualizada
            return serieRepository.save(serieAntigua);
        }).orElseThrow(() -> new RuntimeException("Serie no encontrada con ID: " + idSerie));
    }

}
