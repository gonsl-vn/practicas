package com.viewnext.springbatchf.Repositories;

import com.viewnext.springbatchf.model.Job_execution_log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionLogRepository extends JpaRepository<Job_execution_log, Long> {
}
