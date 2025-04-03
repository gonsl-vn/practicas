package viewnext.practica2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viewnext.practica2.entities.Usuario;
import viewnext.practica2.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    // Inyección de dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Añadir usuario
    @PostMapping
    public ResponseEntity<Usuario> anadirUsuario(@RequestBody Usuario usuario) {
        usuarioRepository.anadirUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // Modificar el usuario por su DNI
    @PutMapping("/{dni}")
    public ResponseEntity<String> modificarUsuarioPorDni(@PathVariable String dni, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.listarUsuarioPorDni(dni);
        if (usuarioExistente == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        usuarioRepository.modificarUsuarioPorDni(dni, usuario);
        return new ResponseEntity<>("Usuario modificado correctamente", HttpStatus.OK);
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Listar usuario por su DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> listarUsuarioPorDni(@PathVariable String dni) {
        Usuario usuario = usuarioRepository.listarUsuarioPorDni(dni);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario);
    }

    // Borrar usuario por DNI
    @DeleteMapping("/{dni}")
    public ResponseEntity<String> borrarUsuarioPorDni(@PathVariable String dni) {
        Usuario usuario = usuarioRepository.listarUsuarioPorDni(dni);
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        usuarioRepository.borrarUsuarioPorDni(dni);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
