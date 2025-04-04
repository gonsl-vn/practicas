package viewnext.practica3.service;

import org.springframework.stereotype.Service;
import viewnext.practica3.entities.Usuario;

import java.util.List;

@Service
public interface UsuarioService {
    List<Usuario> listarUsuarios();

    Usuario listarUsuario(String dni);

    Usuario anadirUsuario(Usuario usuario);

    Usuario modificarUsuario(Usuario usuario);

    void eliminarUsuario(String dni);
}
