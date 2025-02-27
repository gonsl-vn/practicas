package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ProductoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoraRepository extends JpaRepository<ProductoraModel, String> {

   // ProductoraModel findByNombre(String nombre);
    //ProductoraModel findByDirector(String director);

}
