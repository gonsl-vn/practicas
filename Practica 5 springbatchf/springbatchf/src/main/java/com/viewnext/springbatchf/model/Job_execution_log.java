package com.viewnext.springbatchf.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job_execution_log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String distrito;

    private int numeroCasas;

    @Column(name = "estado_batch")
    private String estadoBatch;

}
