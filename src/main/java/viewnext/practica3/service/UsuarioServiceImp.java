package viewnext.practica3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viewnext.practica3.entities.Usuario;
import viewnext.practica3.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioServiceImp implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario listarUsuario(String dni) {
        return usuarioRepository.listarUsuario(dni);
    }

    @Override
    public Usuario anadirUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario modificarUsuario(Usuario usuario) {
        if (usuarioRepository.getByDni(usuario.getDni()) != null) {
            usuarioRepository.delete(usuarioRepository.getByDni(usuario.getDni()));
            return usuarioRepository.save(usuario);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario a modificar no existe");
    }

    @Override
    public void eliminarUsuario(String dni) {
        usuarioRepository.eliminarUsuario(dni);
    }
}

