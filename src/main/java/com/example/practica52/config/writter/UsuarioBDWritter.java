package com.example.practica52.config.writter;

import com.example.practica52.model.Usuario;
import com.example.practica52.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UsuarioBDWritter {

    private final UsuarioRepository usuarioRepository;

    public UsuarioBDWritter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public ItemWriter<Usuario> usuarioABDWritter(){
        log.info("Escribiendo Usuarios en BD");
        return  usuario->{
            usuarioRepository.saveAll(usuario);
        };
    }
}
