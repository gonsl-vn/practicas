package com.viewnext.practicas.P4SeriesYPeliculas.Service;

import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceAlreadyExistsException;
import com.viewnext.practicas.P4SeriesYPeliculas.exception.ResourceNotFoundException;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ProductoraEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ProductoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoraService {
    @Autowired
    private ProductoraRepository productoraRepository;

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
    public ProductoraModel deleteProductora(Integer id) {
        ProductoraModel productoraEncontrada = productoraRepository
                .findById(id);
        if(productoraEncontrada!=null){
            productoraRepository.delete(productoraEncontrada);
        }throw new ResourceNotFoundException("No existe esa productora");
    }
    public ProductoraModel updateProductora(ProductoraModel productoraModel) {
        ProductoraModel productoraEncontrada = productoraRepository
                .findById(productoraModel.getId());
        if(productoraEncontrada==null){
            productoraEncontrada.setName(productoraModel.getName());
            productoraEncontrada.setFoundedInYear(productoraModel.getFoundedInYear());
            productoraEncontrada.setSeries(productoraModel.getSeries());
            productoraEncontrada.setPeliculas(productoraModel.getPeliculas());
            return productoraRepository.save(productoraEncontrada);
        }throw new ResourceNotFoundException("No existe esa productora");
    }

}
