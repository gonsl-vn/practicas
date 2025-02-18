package com.practicas.practica2b.controller;

import com.practicas.practica2b.exception.UserException;
import com.practicas.practica2b.model.User;
import com.practicas.practica2b.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/usuarios")
public class UserController  {

    @Autowired
   private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> todosUsuarios = userRepository.listarUsuarios();
        if(todosUsuarios.isEmpty()){
            throw new UserException(HttpStatus.NOT_FOUND, "no usuarios encontrados");
        }
        return ResponseEntity.ok(todosUsuarios);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<User> listarUsuariosPorDni(@PathVariable String dni) {
        if(dni == null || dni.isEmpty() || dni.length() != 9){
            throw new UserException(HttpStatus.NOT_FOUND, "dni nulo");
        }
        Optional<User> usuarioConDni = userRepository.listarUsuariosPorDni(dni);
        if(usuarioConDni.isPresent()){
            return ResponseEntity.ok(usuarioConDni.get());
        }
        throw new UserException(HttpStatus.NOT_FOUND, "no existe ese usuario");
    }

    @PostMapping("/actualizarUsuario/{dni}")
    public ResponseEntity<User> añadirUsuario(@RequestBody User user) {
        if(user == null ||user.getDni() == null || user.getName() == null
                || user.getSurname() == null || user.getDni().length() != 9){
            throw new UserException(HttpStatus.BAD_REQUEST, "usuario nulo");
        }
        userRepository.añadirUsuario(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/borrarUsuario/{dni}")
    public ResponseEntity<User> borrarUsuarioPorDni(@PathVariable String dni) {
        if(dni == null || dni.isEmpty()){
            throw new UserException(HttpStatus.BAD_REQUEST, "dni nulo");
        }
        boolean borrado = userRepository.borrarUsuarioPorDni(dni);
        if(borrado){
            return ResponseEntity.ok().build();
        }
        throw new UserException(HttpStatus.NOT_FOUND, "no existe ese usuario");
    }

    @PutMapping("/modificarUsuario/{dni}")
    public ResponseEntity<User> modifcarUsuarioPorDni(@PathVariable String dni, @RequestBody User user) {
        Optional<User> usuarioAModificar = userRepository.modificarUsuarioPorDni(user);
        if(usuarioAModificar.isPresent()){
            return ResponseEntity.ok(usuarioAModificar.get());
        }
        throw new UserException(HttpStatus.NOT_FOUND, "no existe ese usuario");
    }

}
