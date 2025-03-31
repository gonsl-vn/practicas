package com.example.practica52.config.processor;

import com.example.practica52.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioProcessorTest {

    @Autowired
    private ItemProcessor<Usuario,Usuario> usuarioAUsuarioProcessor;

    @Test
    void usuarioProcessorTest_shouldReturnTheSameUsuario() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .numPedido(1)
                .name("nombre1")
                .importe(10.0)
                .dni("12345678A")
                .direccion("calle1")
                .codPostal(28970)
                .ciudad("ciudad1")
                .build();
        Usuario usuario2 = Usuario.builder()
                .numPedido(2)
                .name("nombre2")
                .importe(20.0)
                .dni("87654321a")
                .direccion("calle2")
                .codPostal(28223)
                .ciudad("ciudad2")
                .build();

        Usuario usuarioAnswer1 = usuarioAUsuarioProcessor.process(usuario1);
        Usuario usuarioAnswer2 = usuarioAUsuarioProcessor.process(usuario2);

        assertEquals(usuario1, usuarioAnswer1);
        assertEquals(usuario2, usuarioAnswer2);
    }
}
