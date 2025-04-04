package viewnext.practica3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viewnext.practica3.entities.Usuario;
import viewnext.practica3.service.UsuarioServiceImp;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return new ResponseEntity<>(usuarioServiceImp.listarUsuarios(), HttpStatus.OK);
    }

    // Listar un usuario por su DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> listarUsuario(@PathVariable String dni) {
        Usuario usuario = usuarioServiceImp.listarUsuario(dni);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Añadir un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> anadirUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioServiceImp.anadirUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Modificar un usuario existente
    @PutMapping("/{dni}")
    public ResponseEntity<Usuario> modificarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioServiceImp.modificarUsuario(usuario);
        if (usuarioExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Aquí se retorna 404 si no existe el usuario
        }
        Usuario usuarioModificado = usuarioServiceImp.modificarUsuario(usuario);
        return new ResponseEntity<>(usuarioModificado, HttpStatus.OK); // Si todo va bien, retorna 200
    }

    // Eliminar un usuario por su DNI
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String dni) {
        try {
            usuarioServiceImp.eliminarUsuario(dni);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}