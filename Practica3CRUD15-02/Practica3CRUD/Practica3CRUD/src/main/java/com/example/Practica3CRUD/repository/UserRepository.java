package com.example.Practica3CRUD.repository;

import com.example.Practica3CRUD.model.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

   @Query(value = "SELECT * FROM usuarios", nativeQuery = true)
   List<UserModel> listarUsuariosQuery();

   @Query(value = "SELECT * FROM usuarios u WHERE u.dni= :dni", nativeQuery = true)
    Optional<UserModel> listarUsuarioQuery(@Param("dni") String dni);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuarios (dni, name, surname, age ) VALUES (:dni, :name, :surname, :age)", nativeQuery = true)
    Optional<UserModel> añadirUsuarioQuery(@Param("dni") String dni, @Param("name") String name,
            @Param("surname") String surname, @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query(value = "UPDATE usuarios SET dni= :dni", nativeQuery = true)
    Optional<UserModel> modificarUsuario(@Param("dni") String dni,
            @Param("name") String name, @Param("surname") String surname
            , @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarios WHERE dni=:dni", nativeQuery = true)
    void eliminarUsuarioQuery(@Param("dni")  String dni);

    UserModel findByDni(String dni);
}
