package com.viewnext.practica3.repository;

import com.viewnext.practica3.models.Usuario;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository

public class UsersRepository {
    private static List<Usuario> lUsuarios = new ArrayList<>();

    static {
        lUsuarios.add(new Usuario("Juan Carlos", "12345678A", "García", 20));
        lUsuarios.add(new Usuario("María José", "98765432B", "Martínez", 80));
        lUsuarios.add(new Usuario("Pedro Luis", "11111111C", "Díaz", 43));
        lUsuarios.add(new Usuario("Ana Isabel", "22222222D", "González", 89));
        lUsuarios.add(new Usuario("Carlos Alberto", "33333333E", "Rodríguez", 54));
        lUsuarios.add(new Usuario("Sofía Elena", "44444444F", "Gómez", 2));
        lUsuarios.add(new Usuario("Jorge Luis", "55555555G", "Hernández", 87));
        lUsuarios.add(new Usuario("Lucía Sofía", "66666666H", "Sánchez", 15));
        lUsuarios.add(new Usuario("Tomás Juan", "77777777I", "García", 30));
        lUsuarios.add(new Usuario("Eva María", "88888888J", "Martínez", 11));
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

    public void actualizarUsuario(@org.jetbrains.annotations.NotNull Usuario usuario) {
        eliminarUsuario(usuario.getDni());
        agregarUsuario(usuario);
    }
}
