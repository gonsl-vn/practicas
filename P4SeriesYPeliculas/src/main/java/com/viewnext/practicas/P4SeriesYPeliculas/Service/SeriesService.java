package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.SeriesEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.SeriesCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private SeriesCriteriaRepository seriesCriteriaRepository;

    public SeriesEntity convertirAModelo(SeriesModel serie){
        List<String> actores = serie.getActores().stream().map(a->
                (a.getName() + " " + a.getSurname())).collect(Collectors.toList());
        return new SeriesEntity(serie.getTitle(),serie.getCreationYear(),
                actores, (serie.getDirector().getName() + " " +
                serie.getDirector().getSurname())
                , serie.getProductora().getName());
    }

    public List<SeriesEntity> listarSeries(){
        List<SeriesModel> listaSeries = seriesRepository.findAllByOrderByTitleAsc();
        return listaSeries.stream().map(s ->convertirAModelo(s)).collect(Collectors.toList());
    }
    public SeriesModel addSeries(SeriesModel serie){
        SeriesModel serieEncontrada = seriesRepository.findByTitle(serie.getTitle());
        if(serieEncontrada == null){
            seriesRepository.save(serie);
        }throw new ResourceAlreadyExistsException("Ya existe esa serie");
    }
    public SeriesModel deleteSeries(String title){
        SeriesModel serieEncontrada = seriesRepository.findByTitle(title);
        if(serieEncontrada != null){
             seriesRepository.delete(serieEncontrada);
        }throw new ResourceNotFoundException("No existe esa serie");
    }
    public SeriesModel editSeries(SeriesModel serie){
        SeriesModel serieEncontrada = seriesRepository.findByTitle(serie.getTitle());
        if(serieEncontrada!=null){
            serieEncontrada.setTitle(serie.getTitle());
            serieEncontrada.setCreationYear(serie.getCreationYear());
            serieEncontrada.setActores(serie.getActores());
            serieEncontrada.setDirector(serie.getDirector());
            serieEncontrada.setProductora(serie.getProductora());
            seriesRepository.save(serieEncontrada);
        }throw new ResourceNotFoundException("No existe esa serie, creala mejor");

    }
    public Page<SeriesEntity> buscaPorParametros(String title,
            Integer creationYear, String productoraTitle, String directorName,
            String actorName, Pageable pageable) {
        List<SeriesModel> series = seriesCriteriaRepository
                .buscarSeriesPorCriteria(title,
                        creationYear, productoraTitle, directorName, actorName);
        List<SeriesEntity> seriesEnModelo = series.stream().map(
                p->convertirAModelo(p)).collect(Collectors.toList());

        int numSeries = seriesEnModelo.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numSeries);

        List<SeriesEntity> seriesPaginadas =
                seriesEnModelo.subList(start, end);

        return new PageImpl<>(seriesPaginadas, pageable, numSeries);
    }

}
