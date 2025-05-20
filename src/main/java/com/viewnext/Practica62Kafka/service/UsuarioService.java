package com.viewnext.Practica62Kafka.service;

import com.viewnext.Practica62Kafka.model.Usuario;
import com.viewnext.Practica62Kafka.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.types.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registrarUsuario(Usuario usuario){
        Usuario usuarioARegistrar = new Usuario();
        usuarioARegistrar.setIs_prime(usuario.getIs_prime());
        usuarioARegistrar.setNombre(usuario.getNombre());
        log.info(usuario.getPassword());
        String contraCifrada = passwordEncoder.encode(usuario.getPassword());
        log.info("Cotnraseña Cifrada: " + contraCifrada);
        usuarioARegistrar.setPassword(contraCifrada);
        if(usuarioRepository.findByNombre(usuario.getNombre())!=null){
            return false;
        }
        usuarioRepository.save(usuario);
        return true;
    }
    public boolean loginUsuario(Usuario usuario){
        Usuario usuarioRegistrado = usuarioRepository.findByNombre(usuario.getNombre());
        log.info("Contra usu registrado: " + usuarioRegistrado.getPassword());
        log.info("Contra de usuario a loguear: " +  usuario.getPassword());
        return usuarioRegistrado != null && usuario.getPassword().equals(usuarioRegistrado.getPassword());
    }
}
