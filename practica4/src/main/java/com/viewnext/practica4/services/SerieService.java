package com.viewnext.practica4.services;

import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.repositorys.SerieCriteriaRepository;
import com.viewnext.practica4.repositorys.SerieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    private final SerieRepository serieRepository;
    private final SerieCriteriaRepository serieCriteriaRepository;

    public SerieService(SerieRepository serieRepository, SerieCriteriaRepository serieCriteriaRepository) {
        this.serieRepository = serieRepository;
        this.serieCriteriaRepository = serieCriteriaRepository;
    }

    // -------------------- Métodos con JPA Repository --------------------

    public List<Serie> obtenerSeries() {
        return serieRepository.findAll();
    }

    public Optional<Serie> obtenerSeriePorId(int idSerie) {
        return serieRepository.findById(idSerie);
    }

    public void insertarSerie(Serie serie) {
        serieRepository.save(serie);
    }

    public void eliminarSerie(int idSerie) {
        if (serieRepository.findById(idSerie).isPresent()) {
            serieRepository.delete(serieRepository.findById(idSerie).get());
        } else {
            throw new RuntimeException();
        }
    }

    public Serie actualizarSerie(int idSerie, Serie serieActualizada) {
        return serieRepository.findById(idSerie).map(serieAntigua -> {
            serieAntigua.setTitulo(serieActualizada.getTitulo());
            serieAntigua.setAno(serieActualizada.getAno());
            serieAntigua.setDirector(serieActualizada.getDirector());
            serieAntigua.setProductora(serieActualizada.getProductora());
            serieAntigua.setActores(serieActualizada.getActores());
            return serieRepository.save(serieAntigua);
        }).orElseThrow(() -> new RuntimeException("Serie no encontrada con ID: " + idSerie));
    }

    // -------------------- Métodos con Criteria API --------------------

    public List<Serie> obtenerSeriesCriteria() {
        return serieCriteriaRepository.listarSeries();
    }

    public Serie obtenerSeriePorIdCriteria(int idSerie) {
        return serieCriteriaRepository.buscarSerie(idSerie);
    }

    @Transactional
    public void insertarSerieCriteria(Serie serie) {
        serieCriteriaRepository.insertarSerie(serie);
    }

    @Transactional
    public void actualizarSerieCriteria(int idSerie, Serie serieActualizada) {
        serieCriteriaRepository.actualizarSerie(idSerie, serieActualizada);
    }

    @Transactional
    public void eliminarSerieCriteria(int idSerie) {
        serieCriteriaRepository.borrarSeriePorId(idSerie);
    }

    //Pageables

    public Page<Serie> obtenerSerie(Pageable pageable) {
        return serieRepository.findAll(pageable);
    }
}
