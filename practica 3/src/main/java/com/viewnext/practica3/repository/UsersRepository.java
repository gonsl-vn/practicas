package com.viewnext.practica3.repository;

import com.viewnext.practica3.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Usuario, String> {

    // Buscar usuario por nombre
    Optional<Usuario> findByName(String name);

    // Buscar usuario por apellido
    Optional<Usuario> findBySurname(String surname);

    // Buscar usuario por edad
    Optional<Usuario> findByAge(int age);

    Optional<Usuario> findByDni(String dni);

    public default void actualizarUsuario(String dni, Usuario usuarioActualizado) {
        findByDni(dni) // Busca si el usuario existe
                .map(usuario -> {
                    usuario.setName(usuarioActualizado.getName());
                    usuario.setSurname(usuarioActualizado.getSurname());
                    usuario.setAge(usuarioActualizado.getAge());
                    return save(usuario); // JPA detecta que el usuario ya existe y lo actualiza
                });
    }

}
