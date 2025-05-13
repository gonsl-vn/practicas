package com.viewnext.Practica62Kafka.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MensajeConNombreUsu {
    private String mensaje;
    private String nombreUsuario;
}
