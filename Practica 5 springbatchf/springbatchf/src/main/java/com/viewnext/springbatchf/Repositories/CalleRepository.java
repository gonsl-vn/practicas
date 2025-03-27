package com.viewnext.springbatchf.Repositories;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalleRepository extends JpaRepository<Calle, Long> {
}
