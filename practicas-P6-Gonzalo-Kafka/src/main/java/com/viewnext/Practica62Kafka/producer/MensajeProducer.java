package com.viewnext.Practica62Kafka.producer;

import com.viewnext.Practica62Kafka.model.Mensaje;
import com.viewnext.Practica62Kafka.model.MensajeConNombreUsu;
import com.viewnext.Practica62Kafka.model.Usuario;
import com.viewnext.Practica62Kafka.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MensajeProducer {

    private final KafkaTemplate<String, Mensaje> kafkaTemplate;

    private final UsuarioRepository usuarioRepository;

    public void sendMessage(Mensaje mensaje){

       /* Message<Mensaje> messageKafka = MessageBuilder
                .withPayload(mensaje)
                .setHeader(KafkaHeaders.TOPIC, "ReactiveTopic")
                .build();*/
        kafkaTemplate.send("ReactiveTopic", mensaje);
        log.info("Se ha enviado el MENSAJE");
    }

    public void convertirAMensajeYEnviar(MensajeConNombreUsu mensaje){
        Mensaje mensajeAEnviar = new Mensaje();

        mensajeAEnviar.setMessage(mensaje.getMensaje());
        mensajeAEnviar.setUser(mensaje.getNombreUsuario());
        mensajeAEnviar.setTimeStamp(LocalDateTime.now());
        Boolean isPrime = false;
        log.info("Nombre del usuario: " + mensaje.getNombreUsuario());
        if(usuarioRepository.findByNombre(mensaje.getNombreUsuario())!=null) {

            Usuario usuarioEncontrado = usuarioRepository.findByNombre(mensaje.getNombreUsuario());
            log.info("UsuarioEncontrado: " + usuarioEncontrado);
             isPrime = usuarioEncontrado.getIs_prime();
        }

        mensajeAEnviar.setPrimeUser(isPrime);
        sendMessage(mensajeAEnviar);

    }
}
