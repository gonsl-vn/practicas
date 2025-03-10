package com.viewnext.practicas.practica5Batch.repository;

import com.viewnext.practicas.practica5Batch.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalleRepository extends JpaRepository<Calle, String> {

}
