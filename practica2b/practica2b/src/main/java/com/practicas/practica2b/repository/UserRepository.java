package com.practicas.practica2b.repository;

import com.practicas.practica2b.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
@Repository
public class UserRepository{
    private static final List<User> usuarios = new ArrayList<>();

    static{
        usuarios.add(new User("12345678a", "nameEjemplo", "surnameEjemplo"));
        usuarios.add(new User("87654321b", "name2Ejemplo", "surname2Ejemplo"));
    }

    public List<User> listarUsuarios(){
        return new ArrayList<>(usuarios);
        }


    public Optional<User> listarUsuariosPorDni( String dni){
        return usuarios.stream().filter(u -> u.getDni().equals(dni)).findFirst();

    }
    public User añadirUsuario(User user){
        User usuarioNuevo = new User(user.getDni(), user.getName(), user.getSurname());
        usuarios.add(usuarioNuevo   );
        return usuarioNuevo;
    }
    public Optional<User> modificarUsuarioPorDni(User user){
       Optional<User> usuarioAModificar = usuarios.stream().filter(u -> u.getDni().equals(user.getDni())).findFirst();
       usuarioAModificar.ifPresent(u -> u.setName(user.getName()));
       usuarioAModificar.ifPresent(u -> u.setSurname(user.getSurname()));
       return usuarioAModificar;
    }

    public boolean borrarUsuarioPorDni(String dni){
        Optional<User> usuarioABorrar = listarUsuariosPorDni(dni);
        if(usuarioABorrar.isPresent()){
            usuarios.remove(usuarioABorrar.get());
            return true;
        }
        return false;
    }


}

