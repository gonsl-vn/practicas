package com.viewnext.practica3.services;

import com.viewnext.practica3.models.Usuario;
import com.viewnext.practica3.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UsersRepository usersRepository;

    public List<Usuario> listarUsuarios() {
        return usersRepository.findAll();
    }

    public Optional<Usuario> buscarPorNombre(String name) {
        return usersRepository.findByName(name);
    }

    public Optional<Usuario> buscarPorApellido(String surname) {
        return usersRepository.findBySurname(surname);
    }

    public Optional<Usuario> buscarPorEdad(int age) {
        return usersRepository.findByAge(age);
    }

    public Optional<Usuario> buscarPorDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usersRepository.save(usuario);
    }

    public void eliminarUsuario(String dni) {
        usersRepository.deleteById(dni);
    }

    public void actualizarUsuario(String dni, Usuario usuarioActualizado) {
        usersRepository.actualizarUsuario(dni, usuarioActualizado);
    }
}
