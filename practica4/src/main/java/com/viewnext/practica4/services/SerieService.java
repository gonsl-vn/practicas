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

    public Optional<Serie> obtenerSeriePorNombre(String nombre) {
        return serieRepository.findByNombre(nombre);
    }

    public Optional<Serie> obtenerSeriePorId(int idSerie) {
        return serieRepository.findByIdSerie(idSerie);
    }
}
