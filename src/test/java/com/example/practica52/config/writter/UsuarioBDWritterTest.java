package com.example.practica52.config.writter;

import com.example.practica52.model.Usuario;
import com.example.practica52.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioBDWritterTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioBDWritter usuarioBDWritter;
    @Autowired
    private ItemWriter<Usuario> writer;

    @BeforeEach
    void setUp(){
        usuarioRepository.deleteAll();
    }

    @Test
    void usuarioBDWritterTest_shouldSave2Usuario() throws Exception {
        Usuario usuario1 = Usuario.builder()
                .name("Juan Pérez")
                .dni("12345678A")
                .direccion("Calle Falsa 123")
                .ciudad("Madrid")
                .codPostal(28001)
                .importe(100.00)
                .numPedido(1)
                .build();

        Usuario usuario2 = Usuario.builder()
                .name("María García")
                .dni("23456789B")
                .direccion("Calle Real 456")
                .ciudad("Barcelona")
                .codPostal(Integer.valueOf("08001"))//Resulta que cualquier entero que empiece por 08 ò 09 lo detecta como octal y lanza error de parsing
                .importe(200.00)
                .numPedido(2)
                .build();
        List<Usuario> usuarioList = List.of(usuario1,usuario2);

        writer = usuarioBDWritter.usuarioABDWritter();
        writer.write(new Chunk<>(usuarioList));

        List<Usuario> resultados = usuarioRepository.findAll();

        assertEquals(2, resultados.size());
        assertEquals(usuario1,resultados.get(0));
        assertEquals(usuario2, resultados.get(1));
    }

}
