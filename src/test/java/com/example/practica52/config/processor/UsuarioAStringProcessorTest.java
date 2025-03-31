package com.example.practica52.config.processor;

import com.example.practica52.model.Usuario;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioAStringProcessorTest {

    @Autowired
    ItemProcessor<Usuario,String> usuarioStringItemProcessor;

    @Test
    void usuarioAStringProcessorTest_shouldReturnStringFromUsuario() throws Exception {

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
        List<Usuario> usuarioList = List.of(usuario1,usuario2);

        String answer1 = usuarioStringItemProcessor.process(usuario1);
        String answer2 = usuarioStringItemProcessor.process(usuario2);

        List<String> answerList = List.of(answer1,answer2);
        for(int i=0 ; i<2; i++){
            assertEquals("El/La Sr/a " + usuarioList.get(i).getName() +
                    "con Dni " + usuarioList.get(i).getDni() +
                    " residente en " +  usuarioList.get(i).getDireccion() + "," + System.lineSeparator() +
                    " con codigo postal " + usuarioList.get(i).getCodPostal() + "," + System.lineSeparator() +
                    " ha realizado una compra de " + usuarioList.get(i).getImporte() +
                    " euros" + System.lineSeparator() +
                    "con numero de pedido " + usuarioList.get(i).getNumPedido() + ".", answerList.get(i));
        }

    }
}
