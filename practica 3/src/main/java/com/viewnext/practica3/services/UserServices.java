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

    public Usuario buscarPorNombre(String name) {
        return usersRepository.findByName(name);
    }

    public Usuario buscarPorApellido(String surname) {
        return usersRepository.findBySurname(surname);
    }

    public Usuario buscarPorEdad(int age) {
        return usersRepository.findByAge(age);
    }

    public Optional<Usuario> buscarPorDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void guardarUsuario(Usuario usuario) {
        usersRepository.save(usuario);
    }

    public void actualizarUsuario(String dni, Usuario usuarioActualizado) {
        usersRepository.findByDni(dni).ifPresent(usuario -> {
            usuario.setName(usuarioActualizado.getName());
            usuario.setSurname(usuarioActualizado.getSurname());
            usuario.setAge(usuarioActualizado.getAge());
            usersRepository.save(usuario);
        });

    }

    public void eliminarUsuario(String dni) {
        usersRepository.deleteById(dni);
    }

    //Llamadas Nativas

    public List<Usuario> listarUsuariosNativo() {
        return usersRepository.listarUsuariosNativo();
    }

    public Optional<Usuario> buscarPorNombreNativo(String name) {
        return usersRepository.buscarPorNombreNativo(name);
    }

    public Optional<Usuario> buscarPorDniNativo(String dni) {
        return usersRepository.buscarPorDniNativo(dni);
    }

    public Optional<Usuario> buscarPorEdadNativo(int age) {
        return usersRepository.buscarPorEdadNativo(age);
    }

    public void insertarUsuarioNativo(Usuario usuario) {
        usersRepository.insertarUsuarioNativo(usuario.getDni(), usuario.getName(), usuario.getSurname(),
                usuario.getAge());
    }

    public void modificarUsuarioNativo(String dni, Usuario usuarioActualizado) {
        usersRepository.modificarUsuarioNativo(dni, usuarioActualizado.getName(), usuarioActualizado.getSurname(),
                usuarioActualizado.getAge());
    }

    public void borrarUsuarioNativo(String dni) {
        usersRepository.borrarUsuarioNativo(dni);
    }

}
