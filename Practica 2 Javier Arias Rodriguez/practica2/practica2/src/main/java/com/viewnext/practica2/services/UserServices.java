package com.viewnext.practica2.services;

import com.viewnext.practica2.exceptions.InvalidUserException;
import com.viewnext.practica2.models.Usuario;
import com.viewnext.practica2.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private final UsersRepository usersRepository;

    public UserServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Usuario> obtenerUsuarios() {
        return usersRepository.obtenerUsuarios();
    }

    public Usuario obtenerUsuarioPorDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            // Lanza una excepción personalizada si el DNI es nulo o vacío
            throw new InvalidUserException("El DNI no puede ser nulo o vacío.");
        } else if (!esDniValido(dni)) {
            throw new InvalidUserException("El DNI no tiene el formato correcto.");
        }

        Usuario usuario = usersRepository.obtenerUsuarioPorDni(dni);
        if (usuario == null) {
            // Lanza otra excepción personalizada si el usuario no se encuentra
            throw new InvalidUserException(HttpStatus.NOT_FOUND, "Usuario no encontrado con el DNI: " + dni);
        }

        return usuario;

    }

    public void agregarUsuario(Usuario usuario) {
        validarUsuario(usuario); // Valida el usuario antes de continuar
        usersRepository.agregarUsuario(usuario);
    }

    public void eliminarUsuario(String dni) {
        if (dni == null || dni.isEmpty()) {
            throw new InvalidUserException("El DNI no puede ser nulo o vacío.");
        }

        Usuario usuario = usersRepository.obtenerUsuarioPorDni(dni);
        if (usuario == null) {
            throw new InvalidUserException(HttpStatus.NOT_FOUND,
                    "No se puede eliminar, el usuario no existe con el DNI: " + dni);
        }

        usersRepository.eliminarUsuario(dni);

    }

    public void actualizarUsuario(Usuario usuario) {
        validarUsuario(usuario); // Valida el usuario antes de actualizar
        usersRepository.actualizarUsuario(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        // Reglas básicas para validar un usuario:
        if (usuario == null) {
            throw new InvalidUserException("El usuario no puede ser nulo.");
        }
        if (usuario.getName() == null || usuario.getName().isEmpty()) {
            throw new InvalidUserException("El nombre no puede ser nulo o vacío.");
        }
        if (usuario.getDni() == null || usuario.getDni().isEmpty() || !esDniValido(usuario.getDni())) {
            throw new InvalidUserException("El DNI no es válido.");
        }
        if (usuario.getSurname() == null || usuario.getSurname().isEmpty()) {
            throw new InvalidUserException("El apellido no puede ser nulo o vacío.");
        }
    }

    private boolean esDniValido(String dni) {
        if (!dni.matches("\\d{8}[A-Za-z]")) {
            throw new InvalidUserException(
                    "El DNI proporcionado no tiene el formato correcto. Debe tener 8 dígitos seguidos de una letra.");
        }
        return true;

    }

}
