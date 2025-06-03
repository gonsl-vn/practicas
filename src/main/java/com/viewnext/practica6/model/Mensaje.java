package com.viewnext.practica6.model;

import lombok.Data;

import java.time.Instant;

@Data // Utilizamos Data para todas los Getter and Setters y los constructores
public class Mensaje {
    private String user;
    private String message;
    private boolean primeUser;
    private Instant timestamp;

}
