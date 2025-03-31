package com.example.practica52.config.reader;

import com.example.practica52.model.Calle;
import com.example.practica52.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioReaderTest {

    @Autowired
    private UsuarioReader usuarioReader;

    private FlatFileItemReader<Usuario> reader;
    private JobParameters jobParameters;
/*
    @BeforeEach
    void setUp(){
        reader = usuarioReader.usuarioCsvReader();
        reader.open(new ExecutionContext());

        JobParameters jobParameters= new JobParametersBuilder()
                .addLong("hora :", LocalTime.now().toNanoOfDay())
                .toJobParameters();
    }*/

    @Test
    void usuarioReaderTest_shouldRead2Usuarios() throws Exception {
        reader = usuarioReader.usuarioCsvReader();


        JobParameters jobParameters= new JobParametersBuilder()
                .addLong("hora :", LocalTime.now().toNanoOfDay())
                .toJobParameters();

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

        StepExecution stepExecution = MetaDataInstanceFactory
                .createStepExecution(jobParameters);

        StepScopeTestUtils.doInStepScope(stepExecution, ()->{

            reader.open(stepExecution.getExecutionContext());
            Usuario usuarioAnswer1 = reader.read();
            Usuario usuarioAnswer2 = reader.read();

            assertEquals(usuario1,usuarioAnswer1);//Para que funcione tienes que tener EqualsAndHashCode o Data anotation, si no no compara los atributos si no otros valores.
            assertEquals(usuario2,usuarioAnswer2);

            reader.close();
            return  null;
        });

    }
}