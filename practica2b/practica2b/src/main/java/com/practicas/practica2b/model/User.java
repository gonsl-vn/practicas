package com.practicas.practica2b.model;

import lombok.*;
import org.hibernate.annotations.processing.Pattern;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class User {

    private String dni;
    private String name;
    private String surname;
}
