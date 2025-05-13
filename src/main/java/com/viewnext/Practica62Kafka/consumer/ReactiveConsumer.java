package com.viewnext.Practica62Kafka.consumer;

import com.viewnext.Practica62Kafka.config.KafkaStreamConfig;
import com.viewnext.Practica62Kafka.model.Mensaje;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class ReactiveConsumer {

    private Sinks.Many<Mensaje> mensajeSink;
    private Sinks.Many<String> sinkString;
    //private final ConnectableFlux<Mensaje> connectableFlux;

    public ReactiveConsumer(KStream<String, Mensaje> kStream,
            Sinks.Many<Mensaje> mensajeSink) {

        this.mensajeSink = mensajeSink;
        this.sinkString = Sinks.many().multicast().onBackpressureBuffer();
        //this.connectableFlux = connectableFlux;

    }
    public void emitirString(String mensajeString){
        log.info("Emitiendo String desde ReactiveConsumer");
        sinkString.tryEmitNext(mensajeString);
    }

    public Flux<Mensaje> consumeReactivo(){
        log.info("consumeReactivo bien invocado");
       // Flux<Mensaje> fluxTemporal = sinkMensaje.asFlux();

        return mensajeSink.asFlux().doOnSubscribe(s->{log.info("Nuevo suscriptor");})
                .doOnCancel(()->log.warn("FLUX CANCELADO"));
    }

    public Flux<String> consumeStringReactivo(){
        log.info("Entrando a consumeStringReactivo");
       // Flux<String> fluxTemporal = sinkString.asFlux();

        return sinkString.asFlux().doOnSubscribe(s-> log.info("Nuevo suscriptor al string"))
                .doOnCancel(()-> log.info("Se ha cancelado la suscripcion al String"));
    }



/*
    @Bean
    public KStream<String, Mensaje> iniciar(){

        log.info("Iniciandoo");
        kStream.foreach((key,value)->{
            log.info("Recibiendo el mensaje" + value);
            sinkMensaje.tryEmitNext(value);
        });
        return kStream;
    }

    public Flux<Mensaje> pruebaConsumeFluxMensaje(){
        iniciar();
        fluxTemporal = sinkMensaje.asFlux();
        log.info("FLUXTEMPORAL: "+ fluxTemporal);
        return fluxTemporal
                .doOnSubscribe(s->{log.info("Subscriptor nuevo" + s);})
                .doOnCancel(()->log.warn("CANCELANDO FLUX"));


    }*/
    /*
    public Flux<Mensaje> consumeFluxMensaje(){
        fluxTemporal = Flux.create(fluxSink->{
            kStream.foreach((key,value)->{
                log.info("Iniciando FLUXSINK");
                fluxSink.next(value);
            });
            fluxSink.onDispose(()-> log.warn("Fluxsink cancelado"));
            fluxSink.complete();
        });
        fluxTemporal.subscribe(m->log.info("SUSCRITO y recibiendo"));
        return fluxTemporal;
    }
*/
/*
    @PostConstruct
    public Flux<Mensaje> creaFluxMensaje(){
        fluxTemporal= Flux.create(mensaje-> emiteMensaje(mensaje));
        log.info(fluxTemporal.toString());
        return fluxTemporal;
    }


    public void emiteMensaje(FluxSink<Mensaje> mensaje){
        log.info("emitiendo el mensaje " +mensaje);
        kStream.foreach((key,value)-> mensaje.next(value));
        mensaje.onDispose(()->log.warn("FLUX CANCELADO"));

    }

    public Flux<Mensaje> consumeMensajeReactivo(){
        Flux<Mensaje> flux = creaFluxMensaje();
        flux.share();
        flux.doOnSubscribe(s->{log.info("Nuevo SUSCRIPTOR");});
        flux.doOnNext(n->{log.info("Siguiente mensaje" + n);});
        flux.subscribe(mensaje->{
            log.info("Se ha suscrito al endpoint y recibes :" + mensaje);
        });
        return  flux;
    }*/
}
