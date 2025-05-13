package com.viewnext.Practica62Kafka.producer;

import com.viewnext.Practica62Kafka.model.Mensaje;
import com.viewnext.Practica62Kafka.model.MensajeConNombreUsu;
import com.viewnext.Practica62Kafka.model.Usuario;
import com.viewnext.Practica62Kafka.repository.UsuarioRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MensajeProducerTest {

    @Mock
    KafkaTemplate<String, Mensaje> kafkaTemplate;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private MensajeProducer mensajeProducer;

    @Test
    void mensajeProducer_testProduceMensaje_shouldProduceMessageMensaje(){
        LocalDateTime timeNow = LocalDateTime.now();
        Mensaje mensajeTest1 = Mensaje.builder()
                .user("userTest1")
                .primeUser(false)
                .message("MensajeTest1 para testear mensajeProducer")
                .timeStamp(timeNow)
                .build();
        MensajeConNombreUsu mensajeConNombreUsuTest1 = MensajeConNombreUsu
                .builder()
                .mensaje("MensajeTest1 para testear mensajeProducer")
                .nombreUsuario("userTest1")
                .build();
        Usuario usuarioTest1 = Usuario.builder()
                .nombre("userTest1")
                .is_prime(false)
                .build();
/*        Message<Mensaje> messageKafka = MessageBuilder
                .withPayload(mensajeTest1)
                .setHeader(KafkaHeaders.TOPIC, "ReactiveTopic")
                .build();
*/


        when(kafkaTemplate.send("ReactiveTopic",mensajeTest1)).thenReturn(null );
        when(usuarioRepository.findByNombre(mensajeConNombreUsuTest1.getNombreUsuario()))
                .thenReturn(usuarioTest1);

        mensajeProducer.convertirAMensajeYEnviar(mensajeConNombreUsuTest1);

        //Ignora la hora al dar fallo siempre por meter la hora cuando se cambia de modelo MensajeConNombreUsuario a Mensaje
        verify(kafkaTemplate).send(eq("ReactiveTopic"),argThat(m->
                m.getUser().equals("userTest1") &&
                m.getMessage().equals("MensajeTest1 para testear mensajeProducer") &&
                m.getPrimeUser().equals(false)));
    }
}
