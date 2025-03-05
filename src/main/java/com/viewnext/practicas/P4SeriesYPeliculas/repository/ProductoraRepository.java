package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoraRepository extends JpaRepository<ProductoraModel, String> {
    List<ProductoraModel> findAllByOrderByNameAsc();
    ProductoraModel findById(Integer id);
    // ProductoraModel findByNombre(String nombre);
    //ProductoraModel findByDirector(String director);

}
