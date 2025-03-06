package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculaCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculasService {
    @Autowired
    PeliculasRepository peliculasRepository;
    @Autowired
    private PeliculaCriteriaRepository peliculaCriteriaRepository;

    public PeliculasEntity convertirAModelo(PeliculasModel peliculasModel) {
        List<String> actores = peliculasModel.getActores().stream()
                .map(a->(a.getName() +" " + a.getSurname())).collect(Collectors.toList());

        return new PeliculasEntity(peliculasModel.getTitle(),
                peliculasModel.getCreationYear(),
                peliculasModel.getDirector()!=null?
                peliculasModel.getDirector().getName() + " " +
                peliculasModel.getDirector().getSurname():null,
                peliculasModel.getProductora()!=null?
                peliculasModel.getProductora().getName():null,
                actores);
    }
    public List<PeliculasEntity> listarPeliculas() {
        List<PeliculasModel> peliculas = peliculasRepository
                .findAllByOrderByTitleAsc();
        return peliculas.stream().map(p->convertirAModelo(p))
                .collect(Collectors.toList());
    }
    public PeliculasModel addPelicula(PeliculasModel peliculasModel) {
        if(peliculasRepository.findById(peliculasModel.getId())==null) {
           return peliculasRepository.save(peliculasModel);
        }throw new ResourceAlreadyExistsException("La peli ya existe");
    }
    public String deletePelicula(String title) {
        PeliculasModel peliEncontrada = peliculasRepository.findByTitle(title);
        if(peliEncontrada!=null) {
            peliculasRepository.delete(peliEncontrada);
            return "Pelicula eliminada";
        }throw new ResourceNotFoundException("La peli no existe");
    }
    public PeliculasModel editPelicula(PeliculasModel peliculasModel) {
        PeliculasModel peliEncontrada = peliculasRepository.
                findById(peliculasModel.getId());
        if(peliEncontrada!=null) {
            peliEncontrada.setTitle(peliculasModel.getTitle());
            peliEncontrada.setCreationYear(peliculasModel.getCreationYear());
            peliEncontrada.setDirector(peliculasModel.getDirector());
            peliEncontrada.setProductora(peliculasModel.getProductora());
            peliEncontrada.setActores(peliculasModel.getActores());
        }throw new ResourceNotFoundException("La peli no existe");
    }

    public Page<PeliculasEntity> buscaPorParametros(String title,
            Integer creationYear, String productoraTitle, String directorName,
            String actorName, Pageable pageable) {
        List<PeliculasModel> peliculas = peliculaCriteriaRepository
                .buscarPeliculasPorCriteria(title,
                        creationYear, productoraTitle, directorName, actorName);
        List<PeliculasEntity> peliculasEnModelo = peliculas.stream().map(
                p->convertirAModelo(p)).collect(Collectors.toList());

        int numPeliculas = peliculas.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numPeliculas);

        List<PeliculasEntity> peliculasPaginadas =
                peliculasEnModelo.subList(start, end);

        return new PageImpl<>(peliculasPaginadas, pageable, numPeliculas);
    }

}
