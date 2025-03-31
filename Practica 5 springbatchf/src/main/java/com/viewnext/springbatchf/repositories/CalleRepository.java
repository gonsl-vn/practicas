package com.viewnext.springbatchf.repositories;

import com.viewnext.springbatchf.models.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalleRepository extends JpaRepository<Calle, Integer> {
}
