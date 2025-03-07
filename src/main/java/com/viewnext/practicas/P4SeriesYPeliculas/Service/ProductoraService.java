package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ProductoraEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculaCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ProductoraCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ProductoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoraService {
    @Autowired
    private ProductoraRepository productoraRepository;
    @Autowired
    private PeliculaCriteriaRepository peliculaCriteriaRepository;
    @Autowired
    private ProductoraCriteriaRepository productoraCriteriaRepository;

    public ProductoraEntity convertirAModelo(ProductoraModel productoraModel) {
        List<String> peliculas = productoraModel.getPeliculas().stream()
                .map(p->p.getTitle()).collect(Collectors.toList());
        List<String> series = productoraModel.getSeries().stream()
                .map(s->s.getTitle()).collect(Collectors.toList());
        return new ProductoraEntity(productoraModel.getName(),
                productoraModel.getFoundedInYear(),
                peliculas, series);
    }
    public List<ProductoraEntity> listarProductoras() {
        List<ProductoraModel> listaProductoras = productoraRepository.
                findAllByOrderByNameAsc();
        return listaProductoras.stream().map(p->convertirAModelo(p))
                .collect(Collectors.toList());
    }
    public ProductoraModel addProductora(ProductoraModel productoraModel) {
        if(productoraRepository.findById(productoraModel.getId())==null){
            return productoraRepository.save(productoraModel);
        }throw new ResourceAlreadyExistsException("La productora ya existe");
    }
    public String deleteProductora(String name) {
        ProductoraModel productoraEncontrada = productoraRepository
                .findByName(name);
        if(productoraEncontrada!=null){
            productoraRepository.delete(productoraEncontrada);
            return "Productora eliminada";
        }throw new ResourceNotFoundException("No existe esa productora");
    }
    public ProductoraModel updateProductora(String name, ProductoraModel productoraModel) {
        ProductoraModel productoraEncontrada = productoraRepository
                .findByName(name);
        if(productoraEncontrada!=null){
            productoraEncontrada.setName(productoraModel.getName());
            productoraEncontrada.setFoundedInYear(productoraModel.getFoundedInYear());
            productoraEncontrada.setSeries(productoraModel.getSeries());
            productoraEncontrada.setPeliculas(productoraModel.getPeliculas());

             return productoraEncontrada;
        }throw new ResourceNotFoundException("No existe esa productora");
    }

    public Page<ProductoraEntity> buscarProductoraPorParametros(String name,
             Integer foundedInYear, String peliTitle,
            String serieTitle, Pageable pageable){
        List<ProductoraModel> productoras = productoraCriteriaRepository.buscarProductoraPorCriteria(
                name, foundedInYear, peliTitle, serieTitle);

        List<ProductoraEntity> productorasEnModelo = productoras.stream()
                .map(d->convertirAModelo(d)).collect(Collectors.toList());

        int numProductoras= productorasEnModelo.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), numProductoras);
        List<ProductoraEntity> productorasPaginadas = productorasEnModelo.subList(start, end);
        return new PageImpl<>(productorasPaginadas, pageable, numProductoras);
    }

}
