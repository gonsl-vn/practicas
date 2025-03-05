package com.viewnext.practica3.controller;

import com.viewnext.practica3.models.Usuario;
import com.viewnext.practica3.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Voy a manejar solicitudes HTTP
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UserServices userServices;

    public UsuarioController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(userServices.obtenerUsuarios());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> listarUsuarioPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(userServices.obtenerUsuarioPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
        userServices.agregarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String dni, Usuario usuario) {
        userServices.actualizarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> borrarUsuarioPorDni(@PathVariable String dni) {
        userServices.eliminarUsuario(dni);
        return ResponseEntity.ok().build();
    }

}
