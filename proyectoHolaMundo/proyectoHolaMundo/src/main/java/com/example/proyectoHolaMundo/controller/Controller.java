package com.example.proyectoHolaMundo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/say")
public class Controller {
    @GetMapping("/helloWorld")
    public String helloWorld() {return "Hello World";}

}
