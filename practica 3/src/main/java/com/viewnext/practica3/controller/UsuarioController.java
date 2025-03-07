package com.viewnext.practica3.controller;

import com.viewnext.practica3.models.Usuario;
import com.viewnext.practica3.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Voy a manejar solicitudes HTTP
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UserServices userServices;

    public UsuarioController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(userServices.listarUsuarios());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Optional<Usuario>> listarUsuarioPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(userServices.buscarPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
        userServices.guardarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String dni, @RequestBody Usuario usuario) {
        userServices.actualizarUsuario(dni, usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> borrarUsuarioPorDni(@PathVariable String dni) {
        userServices.eliminarUsuario(dni);
        return ResponseEntity.ok().build();
    }

    //Metodos Nativos

    @GetMapping("/nativa")
    public ResponseEntity<List<Usuario>> listarUsuariosNativa() {
        return ResponseEntity.ok(userServices.listarUsuariosNativo());
    }

    @GetMapping("/nativa/{dni}")
    public ResponseEntity<Optional<Usuario>> listarUsuarioPorDniNativa(@PathVariable String dni) {
        return ResponseEntity.ok(userServices.buscarPorDniNativo(dni));
    }

    @PostMapping("/nativa")
    public ResponseEntity<Usuario> agregarUsuarioNativo(@RequestBody Usuario usuario) {
        userServices.insertarUsuarioNativo(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/nativa/{dni}")
    public ResponseEntity<Usuario> actualizarUsuarioNativo(@PathVariable String dni, @RequestBody Usuario usuario) {
        userServices.modificarUsuarioNativo(dni, usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/nativa/{dni}")
    public ResponseEntity<Void> borrarUsuarioPorDniNativo(@PathVariable String dni) {
        userServices.borrarUsuarioNativo(dni);
        return ResponseEntity.ok().build();
    }

}
