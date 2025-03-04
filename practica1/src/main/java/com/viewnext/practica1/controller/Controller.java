package com.viewnext.practica1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hola")
    public String holaMundo() {
        return "Hola Mundo!";

    }
}
