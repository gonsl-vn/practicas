package com.example.practica52.config.processor;

import com.example.practica52.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UsuarioAStringProcessor {

    @Bean
    public ItemProcessor<Usuario, String> usuarioStringItemProcessor(){
        return usuario->{
            return new String("El/La Sr/a " + usuario.getName() +
                    "con Dni " + usuario.getDni() +
                    " residente en " +  usuario.getDireccion() + "," + System.lineSeparator() +
                    " con codigo postal " + usuario.getCodPostal() + "," + System.lineSeparator() +
                    " ha realizado una compra de " + usuario.getImporte() +
                    " euros" + System.lineSeparator() +
                    "con numero de pedido " + usuario.getNumPedido() + "." );

        };
    }
}
