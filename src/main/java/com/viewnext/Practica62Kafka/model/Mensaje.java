package com.viewnext.Practica62Kafka.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "message")
    private String message;
    @Column(name = "prime_user")
    private Boolean primeUser;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
