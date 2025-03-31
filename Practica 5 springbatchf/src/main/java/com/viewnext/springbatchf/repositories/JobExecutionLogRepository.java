package com.viewnext.springbatchf.repositories;

import com.viewnext.springbatchf.models.Job_execution_log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionLogRepository extends JpaRepository<Job_execution_log, Long> {
    // Igualmente, puedes añadir métodos custom si quieres filtrar logs por algo concreto
}
