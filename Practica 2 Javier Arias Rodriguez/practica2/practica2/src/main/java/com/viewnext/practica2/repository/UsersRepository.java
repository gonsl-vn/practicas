package com.viewnext.practica2.repository;

import com.viewnext.practica2.models.Usuario;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository

public class UsersRepository {
    private static List<Usuario> lUsuarios = new ArrayList<>();

    static {
        lUsuarios.add(new Usuario("Juan Carlos", "12345678A", "García"));
        lUsuarios.add(new Usuario("María José", "98765432B", "Martínez"));
        lUsuarios.add(new Usuario("Pedro Luis", "11111111C", "Díaz"));
        lUsuarios.add(new Usuario("Ana Isabel", "22222222D", "González"));
        lUsuarios.add(new Usuario("Carlos Alberto", "33333333E", "Rodríguez"));
        lUsuarios.add(new Usuario("Sofía Elena", "44444444F", "Gómez"));
        lUsuarios.add(new Usuario("Jorge Luis", "55555555G", "Hernández"));
        lUsuarios.add(new Usuario("Lucía Sofía", "66666666H", "Sánchez"));
        lUsuarios.add(new Usuario("Tomás Juan", "77777777I", "García"));
        lUsuarios.add(new Usuario("Eva María", "88888888J", "Martínez"));
    }

    public UsersRepository() {
        // El constructor de esta clase esta vacio ya que no es necesario que tenga ningun campo
    }

    public List<Usuario> obtenerUsuarios() {
        return lUsuarios;
    }

    public Usuario obtenerUsuarioPorDni(String dni) {
        return lUsuarios.stream().filter(u -> u.getDni().equals(dni)).findFirst().orElse(null);
    }

    public void agregarUsuario(Usuario usuario) {
        lUsuarios.add(usuario);
    }

    public void eliminarUsuario(String dni) {
        lUsuarios.removeIf(u -> u.getDni().equals(dni));
    }

    public void actualizarUsuario(Usuario usuario) {
        eliminarUsuario(usuario.getDni());
        agregarUsuario(usuario);
    }
}
