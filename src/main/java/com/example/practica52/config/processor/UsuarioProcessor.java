package com.example.practica52.config.processor;

import com.example.practica52.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UsuarioProcessor {

    @Bean
    public ItemProcessor<Usuario, Usuario> usuarioAUsuarioProcessor(){
        return usuario->  {
           // log.info("procesando usuarios");
            return usuario;
        };
    }
}
