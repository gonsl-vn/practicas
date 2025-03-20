package com.viewnext.Practica62Kafka.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mensaje {
    private String user;
    private String message;
    private Boolean primeUser;
    private LocalDateTime timeStamp;
}
