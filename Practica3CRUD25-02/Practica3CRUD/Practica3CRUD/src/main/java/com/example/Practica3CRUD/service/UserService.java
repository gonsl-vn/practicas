package com.example.Practica3CRUD.service;

import com.example.Practica3CRUD.exception.UserException;
import com.example.Practica3CRUD.model.UserModel;
import com.example.Practica3CRUD.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    public void init() {
        UserModel user1 = new UserModel("23456789E", "nombreEjemplo", "apellidoEjemplo", 22);
        UserModel user2 = new UserModel("12345678F", "nombreEjemplo3", "apellidoEjemplo3", 44);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    public List<UserModel> listarUsuarios(){
        return userRepository.findAll();
    }
    public List<UserModel> listarUsuariosQuery(){
        return userRepository.listarUsuariosQuery();
    }


    public UserModel listarUsuario(String dni){
        UserModel usuarioEncontrado = userRepository.findByDni(dni);
        if(usuarioEncontrado == null){
            throw new UserException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return usuarioEncontrado;
    }
    public UserModel listarUsuarioQuery(String dni){
        UserModel usuarioEncontrado = userRepository.findByDni(dni);
        if(usuarioEncontrado == null){
            throw new UserException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return usuarioEncontrado;
    }

    public UserModel añadirUsuario(UserModel userModel){
        if(userRepository.findByDni(userModel.getDni())==null){
            return userRepository.save(userModel);
        } throw new UserException(HttpStatus.NOT_FOUND, "El usuario ya existe, " +
                "no hace falta añadirlo");

    }
    public UserModel añadirUsuarioQuery(UserModel userModel){
        String dniDelUsuario = userModel.getDni();
        UserModel usuarioEncontrado = userRepository.findByDni(dniDelUsuario);
        if(usuarioEncontrado == null && usuarioEncontrado.getDni()!=null){
            userRepository.añadirUsuarioQuery(usuarioEncontrado.getDni(), usuarioEncontrado.getName(),
            usuarioEncontrado.getSurname(), usuarioEncontrado.getAge());
            return userModel;
        }
        throw new UserException(HttpStatus.NO_CONTENT, "El usuario ya existia no hay que" +
                "añadirlo");
    }



    public UserModel modificarUsuario(UserModel userModel){
        if(userRepository.findByDni(userModel.getDni())!=null){
            userRepository.delete(userRepository.findByDni(userModel.getDni()));
            return userRepository.save(userModel);
        }
        throw new UserException(HttpStatus.NOT_FOUND, "El usuario a modifcar no existe");
    }
    public UserModel modificarUsuarioQuery(UserModel userModel){
        if(userRepository.findByDni(userModel.getDni())!=null){
            userRepository.delete(userRepository.findByDni(userModel.getDni()));
            userRepository.añadirUsuarioQuery(userModel.getDni(), userModel.getName(),
                    userModel.getSurname(), userModel.getAge());
            return userModel;
        }
        throw new UserException(HttpStatus.NOT_FOUND, "El usuario a modifcar no existe");
    }


    public void eliminarUsuario(String dni){
        if(userRepository.findByDni(dni)!=null){
            userRepository.delete(userRepository.findByDni(dni));
        }throw new UserException(HttpStatus.NOT_FOUND, "El usuario a eliminar no existia");

    }
    public void eliminarUsuarioQuery(String dni){
        if(userRepository.findByDni(dni)!=null){
            userRepository.eliminarUsuarioQuery(dni);
        }
        throw new UserException(HttpStatus.NOT_FOUND, "El usuario a eliminar no existe");
    }
}
