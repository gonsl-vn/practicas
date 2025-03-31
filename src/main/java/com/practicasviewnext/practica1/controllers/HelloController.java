package com.practicasviewnext.practica1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/say/helloWorld")
    public String hello() {
        return "Hello World";
    }

}
