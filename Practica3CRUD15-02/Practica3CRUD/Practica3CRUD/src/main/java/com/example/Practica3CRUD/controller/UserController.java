package com.example.Practica3CRUD.controller;

import com.example.Practica3CRUD.model.UserModel;
import com.example.Practica3CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> listarUsuarios() {
        return ResponseEntity.ok(userService.listarUsuariosQuery());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<UserModel> listarUsuario(@PathVariable String dni){
        return ResponseEntity.ok(userService.listarUsuario(dni));
    }

    @GetMapping("/query/{dni}")
    public ResponseEntity<UserModel> listarUsuarioQuery(@PathVariable String dni){
        return ResponseEntity.ok(userService.listarUsuarioQuery(dni));
    }

    @PostMapping("/post")
    public ResponseEntity<UserModel> añadirUsuario(
            @RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.añadirUsuario(userModel));
    }

    @PostMapping("/query/post")
    public ResponseEntity<UserModel> añadirUsuarioQuery(
            @RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.añadirUsuarioQuery(userModel));
    }

    @PutMapping("/put/{dni}")
    public ResponseEntity<UserModel> modificarUsuario(@PathVariable String dni,
            @RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.modificarUsuario(userModel));
    }

    @PutMapping("/query/put/{dni}")
    public ResponseEntity<UserModel> modificarUsuarioQuery(@PathVariable String dni,
            @RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.modificarUsuarioQuery(userModel));
    }

    @DeleteMapping("/delete/{dni}")
    public void eliminarUsuario(@PathVariable String dni){
        userService.eliminarUsuario(dni);
    }

    @DeleteMapping("/query/delete/{dni}")
    public void eliminarUsuarioQuery(@PathVariable String dni){
        userService.eliminarUsuarioQuery(dni);
    }

}
