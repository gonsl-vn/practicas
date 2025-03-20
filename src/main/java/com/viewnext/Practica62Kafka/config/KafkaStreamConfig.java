package com.viewnext.Practica62Kafka.config;

import com.viewnext.Practica62Kafka.model.Mensaje;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import reactor.core.publisher.Sinks;

import java.security.Key;

@Configuration
public class KafkaStreamConfig {

    private static final String topic = "ReactiveTopic";

    @Bean
    public StreamsBuilder streamsBuilder()
    {
        return  new StreamsBuilder();
    }
    @Bean
    public KStream<String, Mensaje> kStream(StreamsBuilder streamsBuilder){
        JsonSerde<Mensaje> jsonSerde = new JsonSerde<>(Mensaje.class);
        KStream<String, Mensaje> kstreamo = streamsBuilder.stream("PersistanceTopic", Consumed.with(Serdes.String(),
                jsonSerde));
        return kstreamo;
    }
    @Bean
    public Sinks.Many<Mensaje> mensajeSink(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
