package com.viewnext.Practica62Kafka.config;

import com.viewnext.Practica62Kafka.model.Mensaje;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.kafka.support.serializer.JsonSerde;
import reactor.core.publisher.Sinks;

import java.security.Key;
import java.util.Properties;

@Slf4j
@Configuration
public class KafkaStreamConfig {

    private static final String topic = "ReactiveTopic";

    @Bean
    public StreamsBuilder streamsBuilder()
    {
        return  new StreamsBuilder();
    }
    /*
    @Bean
    public KStream<String, Mensaje> kStream(StreamsBuilder streamsBuilder){
        JsonSerde<Mensaje> jsonSerde = new JsonSerde<>(Mensaje.class);
        KStream<String, Mensaje> kstreamo = streamsBuilder.stream("ReactiveTopic", Consumed.with(Serdes.String(),
                jsonSerde));

        return kstreamo;
    }*/
    @Bean
    public Sinks.Many<Mensaje> mensajeSink(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "PracticaKafka");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);

        Topology topology = streamsBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology,properties);
        streams.start();
        return streams;
    }

    @Bean
    public KStream<String, Mensaje> kStream(StreamsBuilder streamsBuilder, Sinks.Many<Mensaje> sink){
        JsonSerde<Mensaje> jsonSerde = new JsonSerde<>(Mensaje.class);
        jsonSerde.deserializer().addTrustedPackages("*");

        KStream<String, Mensaje> stream= streamsBuilder.stream("ReactiveTopic", Consumed.with(Serdes.String(),jsonSerde));

        stream.foreach((key,value)-> {log.info("CONSUMIENDO EL MENSAJE " + value);
        sink.tryEmitNext(value);});
        return stream;

    }
}
