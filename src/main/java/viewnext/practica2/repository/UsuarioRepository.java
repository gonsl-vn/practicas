package viewnext.practica2.repository;

import org.springframework.stereotype.Repository;
import viewnext.practica2.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("Juan", "Pérez", "12345678A"));
        usuarios.add(new Usuario("María", "López", "87654321B"));
        usuarios.add(new Usuario("Carlos", "García", "11223344C"));
    }

    //método para listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    //método para buscar el usuario por el dni
    public Usuario listarUsuarioPorDni(String dni) {
        for (Usuario usuario : usuarios) {
            if (usuario.getDni().equals(dni)) {
                return usuario;
            }
        }
        return null;
    }

    //método para añadir un nuevo usuario
    public void anadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    //método para modificar un usuario buscandolo por su dni
    public void modificarUsuarioPorDni(String dni, Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getDni().equals(dni)) {
                usuario.setNombre(usuarioActualizado.getNombre());
                usuario.setApellido(usuarioActualizado.getApellido());
            }
        }
    }

    //método para borrar un usuario por su dni
    public void borrarUsuarioPorDni(String dni) {
        usuarios.removeIf(usuario -> usuario.getDni().equals(dni));
    }
}

