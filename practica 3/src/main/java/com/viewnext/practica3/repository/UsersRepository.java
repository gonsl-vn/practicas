package com.viewnext.practica3.repository;

import com.viewnext.practica3.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    //Query Nativas -> @Query

    @Query(nativeQuery = true, value = "select * from Usuario")
    List<Usuario> listarUsuariosNativo();

    @Query(nativeQuery = true, value = "Select * from Usuario where nombre = ?1")
    Optional<Usuario> buscarPorNombreNativo(String nombre);

    @Query(nativeQuery = true, value = "Select * from Usuario where surname = ?1")
    Optional<Usuario> buscarPorSurnameNativo(String surname);

    @Query(nativeQuery = true, value = "Select * from Usuario where dni = ?1")
    Optional<Usuario> buscarPorDniNativo(String dni);

    @Query(nativeQuery = true, value = "Select * from Usuario where edad = ?1")
    Optional<Usuario> buscarPorEdadNativo(int edad);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO usuario (dni, nombre, surname, age) VALUES ('?1', '?2', '?3',?4 );")
    void insertarUsuarioNativo(String dni, String nombre, String surname, int age);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE usuario SET nombre = ?2, surname = ?3, age = ?4 WHERE dni = ?1;")
    Optional<Usuario> modificarUsuarioNativo(String dni, String nombre, String surname, int age);

    @Query(nativeQuery = true, value = "DELETE FROM usuario WHERE dni = ?1")
    void borrarUsuarioNativo(String dni);

}
