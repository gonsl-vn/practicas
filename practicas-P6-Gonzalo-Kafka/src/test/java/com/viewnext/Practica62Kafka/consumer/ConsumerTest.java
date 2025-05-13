package com.viewnext.Practica62Kafka.consumer;

import com.viewnext.Practica62Kafka.model.Mensaje;
import com.viewnext.Practica62Kafka.model.MensajeConNombreUsu;
import com.viewnext.Practica62Kafka.model.Usuario;
import com.viewnext.Practica62Kafka.producer.MensajeProducer;
import com.viewnext.Practica62Kafka.repository.UsuarioRepository;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.awaitility.Durations.FIVE_SECONDS;
import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "ReactiveTopic")
public class ConsumerTest {


    //POSIBLE MOCK private KafkaTemplate<String, Mensaje> kafkaTemplate;
    @Mock
    private ReactiveConsumer reactiveConsumer;

    private Sinks.Many<Mensaje> mensajeSink;
    @Mock
    private KafkaTemplate<String, Mensaje> kafkaTemplate;
    @Mock
    private UsuarioRepository usuarioRepository;
    private KStream<String, Mensaje> kStreams;
    // private KStream<String, Mensaje> kStream;
    @InjectMocks
    private MensajeProducer mensajeProducer;

    @BeforeEach
    void setUp() {

        mensajeSink = Sinks.many().multicast().onBackpressureBuffer();
        //reactiveConsumer = new ReactiveConsumer(kStreams, mensajeSink);
    }

    @Test
    void reactiveConsumer_ConsumesReactively_shouldConsumeMensaje(){

        Mensaje mensajeTestReactivo2 = Mensaje.builder().
                user("userTest2").primeUser(false)
                .message("MensajeReactivo2 de Test")
                .timeStamp(LocalDateTime.now())
                .build();
        Mensaje mensajeTestReactivo3 = Mensaje.builder().
                        user("userTest3").primeUser(false)
                .message("MensajeReactivo3 de Test")
                .timeStamp(LocalDateTime.now())
                .build();
        MensajeConNombreUsu mensajeConNombreUsuTest1 = MensajeConNombreUsu
                .builder()
                .mensaje("MensajeTest3 para testear reactiveConsumer")
                .nombreUsuario("userTest1")
                .build();
        MensajeConNombreUsu mensajeConNombreUsuTest2 = MensajeConNombreUsu
                .builder()
                .mensaje("MensajeTest2 para testear reactiveConsumer")
                .nombreUsuario("userTest2")
                .build();
        Usuario usuarioTest1 = Usuario.builder()
                .nombre("userTest1")
                .is_prime(false)
                .build();
        Usuario usuarioTest2 = Usuario.builder()
                .nombre("userTest2")
                .is_prime(false)
                .build();

        when(usuarioRepository.findByNombre("userTest1")).thenReturn(usuarioTest1);
        when(usuarioRepository.findByNombre("userTest2")).thenReturn(usuarioTest2);

        when(kafkaTemplate.send("ReactiveTopic", mensajeTestReactivo2)).thenReturn(null);
        when(kafkaTemplate.send("ReactiveTopic", mensajeTestReactivo3)).thenReturn(null);


        reactiveConsumer.consumeReactivo();

        mensajeProducer.convertirAMensajeYEnviar(mensajeConNombreUsuTest1);
        mensajeProducer.convertirAMensajeYEnviar(mensajeConNombreUsuTest2);

        verify(reactiveConsumer).consumeReactivo();

        //kafkaTemplate.send("ReactiveTopic", mensajeTestReactivo2);
       // kafkaTemplate.send("ReactiveTopic", mensajeTestReactivo3);

       // mensajeSink.tryEmitNext(mensajeTestReactivo2);
        //mensajeSink.tryEmitNext(mensajeTestReactivo3);
        //reactiveConsumer.consumeReactivo();//.publishOn(Schedulers.parallel());
      /*  StepVerifier.create(reactiveConsumer.consumeReactivo()).then(()->{
            mensajeProducer.convertirAMensajeYEnviar(mensajeConNombreUsuTest1);
            mensajeProducer.convertirAMensajeYEnviar(mensajeConNombreUsuTest2);
                })
                //.then(()-> {
        //  mensajeSink.tryEmitNext(mensajeTestReactivo2);
        //          mensajeSink.tryEmitNext(mensajeTestReactivo3);
                //      })
                .expectNext(mensajeTestReactivo2)
                .expectNext(mensajeTestReactivo3)
                .thenCancel()
                .verify();*/
    }
}
